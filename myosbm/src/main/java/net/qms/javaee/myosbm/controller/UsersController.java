package net.qms.javaee.myosbm.controller;

import com.alibaba.fastjson.JSON;
import net.qms.javaee.myosbm.entity.Users;
import net.qms.javaee.myosbm.service.UsersService;
import net.qms.javaee.myosbm.utils.AuthCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
public class UsersController {
    @Resource
    private UsersService usersService;

    //HTTP CLIENT
    private RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private StringRedisTemplate redisTemplate;


    /**
     * 用户注册
     * @param tel 用户要验证的手机号
     * @param authCode 用户输入的验证码
     * @param loginCode 用户在微信端获取的登录code
     * @param nickName 用户昵称
     */
    @RequestMapping("/register")
    public Map<String, Object> register(@RequestParam String tel,
                                     @RequestParam String authCode,
                                     @RequestParam(name = "code") String loginCode,
                                     @RequestParam String nickName){
        Map<String, Object> map = new HashMap<>();

        //校验验证码
        String correctCode = redisTemplate.opsForValue().get(tel);

        if(!authCode.equals(correctCode)){
           map.put("msg", "验证码不正确");
           map.put("res", "fail");
           return map;
        }

        Map jsonMap = usersService.requestForWXLogin(restTemplate, loginCode);

        if(jsonMap.get("openid") == null || jsonMap.get("session_key") == null){
            map.put("msg", "请求服务失败");
            map.put("res", "fail");
            return map;
        }

        Users user = new Users((String)jsonMap.get("openid"), (String)jsonMap.get("session_key"),
                tel, nickName, Users.USER_NOT_PAIED, Users.USER_RENT);

        Optional<Users> existUser = usersService.findByOpenId((String) jsonMap.get("openid"));

        if(existUser.isPresent()){
            map.put("res", "success");
            map.put("isExist", true);
            map.put("msg", "用户已注册过，请直接登录");
            return map;
        }

        Users storeRes = usersService.register(user);

        if(storeRes == null){
            map.put("msg", "注册失败");
            map.put("res", "fail");

        }else {
            map.put("res", "success");
            map.put("isExist", false);
        }

        return map;
    }


    /**
     * 发送验证码信息，并存入redis
     */
    @RequestMapping("getAuthCode")
    public Map<String, Object> sendAuthCode(@RequestParam String mobile){
        Map<String, Object> map = new HashMap<>();

        try {
            String s = AuthCodeUtils.sendCodeTo(mobile);

            Map jsonMap = JSON.parseObject(s, Map.class);
            System.out.println(jsonMap);

            System.out.println(jsonMap.get("code"));

            if(jsonMap.get("code").equals(200)){
                //将验证码存入redis, 有效期为10分钟
                redisTemplate.opsForValue().set(mobile, (String) jsonMap.get("obj"),
                        60 * 10, TimeUnit.SECONDS); //测试时可延长有效时间

                map.put("res", "success");
                map.put("code", 200);
//                map.put("authCode", jsonMap.get("obj"));

            }else {
                map.put("res", "fail");
                map.put("code", jsonMap.get("code"));
            }

        } catch (Exception e) {
            map.put("res", "fail");
        }

        return map;
    }


    @RequestMapping("/login")
    public Map<String, Object> login(@RequestParam(name = "code") String loginCode){
        Map<String, Object> map = new HashMap<>();

        Map jsonMap = usersService.requestForWXLogin(restTemplate, loginCode);

        if(jsonMap.get("openid") == null || jsonMap.get("session_key") == null){
            map.put("msg", "请求服务失败");
            map.put("res", "fail");
            return map;
        }

//        System.out.println((String) jsonMap.get("openid"));

        Boolean loginSuccess = usersService.login((String) jsonMap.get("openid"));

        if(loginSuccess){
            usersService.updateSessionKey((String) jsonMap.get("openid"), (String) jsonMap.get("session_key"));
            map.put("msg", "登录成功");
            map.put("res", "success");
            map.put("sessionKey", jsonMap.get("session_key"));

        }else {
            map.put("msg", "该账号还没验证手机，请先在注册页面验证手机");
            map.put("res", "fail");
        }

        return map;
    }


    @RequestMapping("/getRentInfo")
    @ResponseBody
    public Map<String, Object> getRentInfo(@RequestParam String sessionKey){
        Map<String, Object> map = new HashMap<>();

        Optional<Users> usersOptional = usersService.findBySessionKey(sessionKey);

        if(usersOptional.isPresent()){
            Users user = usersOptional.get();
            Double rent = user.getRent();
            Integer uStatus = user.getUStatus();

            map.put("res", "success");
            map.put("rent", rent);
            map.put("userStatus", uStatus);

        }else {
            map.put("res", "failed");
            map.put("msg", "没有该用户,请重新授权");
        }

        return map;
    }


    @RequestMapping("/rentBack")
    @ResponseBody
    public Map<String, Object> rentBack(@RequestParam String sessionKey){
        return updateRentStatus(sessionKey, Users.USER_NOT_PAIED);
    }


    @RequestMapping("/rentPay")
    @ResponseBody
    public Map<String, Object> rentPay(@RequestParam String sessionKey){
        return updateRentStatus(sessionKey, Users.USER_NORMAL);
    }


    private Map<String, Object> updateRentStatus(String sessionKey, Integer ustatus){
        Map<String, Object> map = new HashMap<>();

        Optional<Users> usersOptional = usersService.findBySessionKey(sessionKey);

        if(usersOptional.isPresent()){
            Users user = usersOptional.get();
            user.setUStatus(ustatus);
            usersService.updateUser(user);

            map.put("res", "success");

        }else {
            map.put("res", "failed");
        }

        return map;
    }
}
