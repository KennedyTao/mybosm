package net.qms.javaee.myosbm.controller;

import net.qms.javaee.myosbm.entity.Bike;
import net.qms.javaee.myosbm.entity.Lease;
import net.qms.javaee.myosbm.entity.Users;
import net.qms.javaee.myosbm.service.BikeService;
import net.qms.javaee.myosbm.service.LeaseService;
import net.qms.javaee.myosbm.service.UsersService;
import net.qms.javaee.myosbm.utils.Tools;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

@Controller
@RequestMapping("/bike")
public class BikeController {
    @Resource
    private BikeService bikeService;
    @Resource
    private UsersService usersService;
    @Resource
    private LeaseService leaseService;


    @RequestMapping("/toAdd")
    public String toAdd(Model model,@RequestParam(defaultValue = "23.054314", required = false) String lat,
                        @RequestParam(defaultValue = "113.396834", required = false) String log){
        Example<Bike> example = Example.of(new Bike().setBStatus(Bike.BIKESTATUS_NORMAL));
        List<Bike> list = bikeService.listAllBy(example);

        model.addAttribute("bikeList", list);
        model.addAttribute("lat", lat);
        model.addAttribute("log", log);
        return "bikeAdd";

    }


    /**
     * 添加单车坐标
     * @param latitude 单车的经度(东经)
     * @param longitude 单车的纬度(北纬)
     */
    @ResponseBody
    @RequestMapping("/add")
    public Map<String, Object> add(@RequestParam String latitude, @RequestParam String longitude){
        Map<String, Object> map = new HashMap<>();

        Bike bike = new Bike(Bike.BIKESTATUS_NORMAL, new BigDecimal(latitude), new BigDecimal(longitude));

        Bike res = bikeService.addBike(bike);

        if (res != null){
            map.put("res", "success");

        }else {
            map.put("res", "failed");
            map.put("msg", "添加失败，请重试");
        }

        return map;
    }


    @RequestMapping("/del")
    @ResponseBody
    public Map<String, Object> delete(@RequestParam Integer bno){
        Map<String, Object> map = new HashMap<>();

        Integer res = bikeService.findById(bno).map(Bike::getBno).orElse(null);

        if(res == null){
            map.put("res", "failed");
            map.put("msg", "没有此id");

        }else {
            bikeService.delete(res);
            map.put("res", "success");
        }

        return map;

    }


    /**
     * 查找200米内没有使用的单车
     */
    @RequestMapping("/findByLocation")
    @ResponseBody
    public Map<String, Object> findBikeBy(@RequestParam String log,@RequestParam String lat){
        Map<String, Object> map = new HashMap<>();

        BigDecimal longitude = new BigDecimal(log);
        BigDecimal latitude = new BigDecimal(lat);

        List<Bike> bikeList = bikeService.findBikeIn(longitude, latitude);

        map.put("bikeList", bikeList);
        return map;
    }


    @RequestMapping("/unlock")
    @ResponseBody
    public Map<String, Object> unlock(@RequestParam String bno, @RequestParam String sessionKey){
        Map<String, Object> map = new HashMap<>();

        try {
            Integer num = Integer.parseInt(bno);

            Boolean res = bikeService.unLock(num);

            if (res) {
                //查找用户信息
                Integer uno = usersService.findBySessionKey(sessionKey).map(Users::getUno).orElse(null);

                if (uno == null) {
                    map.put("res", "fail");
                    map.put("msg", "当前用户不正确或会话过期，请重新登录");

                } else {
                    Lease lease = new Lease(num, uno, Tools.toTimeStamp(new Date()), null, null, 0d, Lease.UNPAY);

                    Lease resLease = leaseService.add(lease);

                    if (resLease == null) {
                        map.put("res", "fail");
                        map.put("msg", "生成订单失败，请重试");

                    } else {
                        map.put("lno", resLease.getLno());
                        map.put("res", "success");
                        map.put("usingBike", num);
                    }
                }

            } else {
                map.put("res", "fail");
                map.put("msg", "没有该自行车或当前不能被使用");
            }
//            }
        }catch(NumberFormatException e){
            map.put("res", "fail");
            map.put("msg", "请输入正确的单车号");
        }

        return map;
    }


    @RequestMapping("createOrder")
    @ResponseBody
    public Map<String, Object> createLease(@RequestParam Integer lno,
                                           @RequestParam Integer bno,
                                           @RequestParam String hours,
                                           @RequestParam String minutes,
                                           @RequestParam String seconds,
                                           @RequestParam String lat,
                                           @RequestParam String log){

        Map<String, Object> map = new HashMap<>();

        BigDecimal longitude = new BigDecimal(log);
        BigDecimal latitude = new BigDecimal(lat);

        long feeSeconds = Integer.parseInt(hours) * 60 + Integer.parseInt(minutes) * 60 + Integer.parseInt(seconds);

        Optional<Lease> leaseOptional = leaseService.findByLno(lno);

        if (leaseOptional.isPresent()){
            Lease lease = leaseOptional.get();

            Timestamp startTime = lease.getStartTime();
            Timestamp endTime = new Timestamp(feeSeconds * 1000 + startTime.getTime());

            lease.setEndTime(endTime);
            lease.setSpendTime(feeSeconds);

            long spendHours = feeSeconds / 3600;
            long restTime = feeSeconds % 3600;

            if (restTime != 0){
                spendHours = spendHours + 1;
            }

            double cost = Double.parseDouble(String.valueOf(spendHours));

            lease.setCost(cost);

            Lease resLease = leaseService.update(lease);

            if (resLease != null){
                //关锁
                bikeService.lock(bno);
                //改变单车位置
                bikeService.changeLocation(longitude, latitude, bno);

                map.put("res", "success");
                map.put("cost", cost);
                map.put("lno", resLease.getLno());

            }else {
                map.put("res", "fail");
                map.put("msg", "更新订单状态失败，请重试");
            }

        }else {
            map.put("res", "fail");
            map.put("msg", "订单不存在");
        }
        return map;
    }


    @RequestMapping("/settleLease")
    @ResponseBody
    public Map<String, Object> settleLease(@RequestParam Integer lno){
        Map<String, Object> map = new HashMap<>();

        Optional<Lease> leaseOptional = leaseService.findByLno(lno);

        if (leaseOptional.isPresent()) {
            Lease lease = leaseOptional.get();

            lease.setLStatus(Lease.PAIED);
            Lease lease1 = leaseService.update(lease);

            if (lease1 != null){
                map.put("res", "success");

            }else {
                map.put("res", "fail");
                map.put("msg", "更新订单状态失败，请重试");
            }

        }else {
            map.put("res", "fail");
            map.put("msg", "订单不存在");
        }

        return map;
    }


    @RequestMapping("/hasRent")
    @ResponseBody
    public Map<String, Object> hasRent(String sessionKey){
        Map<String, Object> map = new HashMap<>();

        Optional<Users> usersOptional = usersService.findBySessionKey(sessionKey);

        if (usersOptional.isPresent()){
            Users user = usersOptional.get();

            if(user.getUStatus() == Users.USER_NORMAL){
                map.put("res", "success");
                return map;
            }
        }

        map.put("res", "fail");
        return map;
    }
}
