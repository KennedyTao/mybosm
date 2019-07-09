package net.qms.javaee.myosbm.controller;

import net.qms.javaee.myosbm.entity.Lease;
import net.qms.javaee.myosbm.entity.Users;
import net.qms.javaee.myosbm.service.LeaseService;
import net.qms.javaee.myosbm.service.UsersService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/lease")
public class LeaseController {
    @Resource
    private LeaseService leaseService;
    @Resource
    private UsersService usersService;


    @RequestMapping("/toLeaseInfo")
    public String toLeaseInfo(Model model, @RequestParam(defaultValue = "0") String currentPage){

        Pageable pageable = PageRequest.of(Integer.parseInt(currentPage), 15);
        Page<Lease> leasePage = leaseService.listByPage(Example.of(new Lease()), pageable);

        model.addAttribute("leasePage", leasePage);

        return "leaseInfo";
    }


    @RequestMapping("/toInsufficientLease")
    public String toInsufficientLease(Model model, @RequestParam(defaultValue = "0") String currentPage){

        Pageable pageable = PageRequest.of(Integer.parseInt(currentPage), 15);
        Page<Lease> leasePage = leaseService.listByPage(Example.of(new Lease().setLStatus(Lease.UNPAY)), pageable);

        model.addAttribute("leasePage", leasePage);

        return "insufficientLease";
    }


    @RequestMapping("/listByUser")
    @ResponseBody
    public Map<String, Object> getLeaseByUser(@RequestParam String sessionKey){
        Map<String, Object> map = new HashMap<>();

        Integer uno = usersService.findBySessionKey(sessionKey).map(Users::getUno).orElse(null);

        if (uno == null){
            map.put("res", "fail");
            map.put("msg", "用户不存在");
        }

        List<Lease> leaseList = leaseService.listByUser(uno);

        if (leaseList != null){
            map.put("res", "success");
            map.put("hasLease", true);
            map.put("leaseList", leaseList);

        }else {
            map.put("res", "success");
            map.put("hasLease", false);
        }

        return map;
    }
}
