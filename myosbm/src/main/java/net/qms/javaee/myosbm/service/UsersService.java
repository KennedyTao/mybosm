package net.qms.javaee.myosbm.service;

import com.alibaba.fastjson.JSON;
import net.qms.javaee.myosbm.dao.UsersDao;
import net.qms.javaee.myosbm.entity.Users;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class UsersService {
    @Resource
    private UsersDao usersDao;


    public List<Users> find(){
        return usersDao.findAll();
    }


    public Optional<Users> findByOpenId(String openId){
        return usersDao.findByOpenId(openId);
    }

    public Users register(Users user) {
        return usersDao.save(user);
    }


    public Map requestForWXLogin(RestTemplate restTemplate, String loginCode){
         /*
            请求地址： https://api.weixin.qq.com/sns/jscode2session?
                        appid=APPID&
                        secret=SECRET&
                        js_code=JSCODE&
                        grant_type=authorization_code
         */
        String url = "https://api.weixin.qq.com/sns/jscode2session";

        String appId = "*************";
        //TODO 秘钥在提交前删掉！
        String secret = "*******************";
        String grantType = "authorization_code";

        //此处要用get请求
        String wxResult = restTemplate.getForObject(url + "?appid=" + appId + "&secret=" + secret + "&js_code="
                + loginCode + "&grant_type=" + grantType, String.class);

        return JSON.parseObject(wxResult, Map.class);
    }


    public Boolean login(String openId){
        return usersDao.findByOpenId(openId).isPresent();
    }


    public void updateSessionKey(String openId, String sessionKey){
        usersDao.updateSessionKeyByOpenId(openId, sessionKey);
    }


    public Page<Users> listUsers(Example<Users> example, Pageable pageable){
        return usersDao.findAll(example, pageable);
    }


    public Optional<Users> findBySessionKey(String sessionKey){
        return usersDao.findBySessionKey(sessionKey);
    }


    public void updateUser(Users user){
        usersDao.saveAndFlush(user);
    }
}
