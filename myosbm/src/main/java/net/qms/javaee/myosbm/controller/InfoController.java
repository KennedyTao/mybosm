package net.qms.javaee.myosbm.controller;

import net.qms.javaee.myosbm.entity.Info;
import net.qms.javaee.myosbm.service.InfoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/info")
public class InfoController {
    @Resource
    private InfoService infoService;


    @RequestMapping("/get")
    @ResponseBody
    public Map<String, Object> getInfo(){
        Map<String, Object> map = new HashMap<>();
        Optional<Info> info = infoService.findById(1);

        info.ifPresent(i -> map.put("info", i));
        return map;
    }
}
