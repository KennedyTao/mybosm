package net.qms.javaee.myosbm.controller;

import net.qms.javaee.myosbm.entity.Admin;
import net.qms.javaee.myosbm.entity.Info;
import net.qms.javaee.myosbm.entity.Users;
import net.qms.javaee.myosbm.service.AdminService;
import net.qms.javaee.myosbm.service.InfoService;
import net.qms.javaee.myosbm.service.UsersService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Resource
    private AdminService adminService;

    @Resource
    private UsersService usersService;

    @Resource
    private InfoService infoService;


    @RequestMapping("/index")
    public String toIndex(){
        return "login";
    }


    @RequestMapping("/toLogin")
    public String toLogin(){
        return "login";
    }


    @RequestMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password,
                        Model model, HttpSession session){
        Optional<Admin> admin = adminService.Login(username, password);

        if (admin.isPresent()){
            session.setAttribute("admin", admin.get().getAno());
            return "redirect:/admin/listUsers";

        }else {
            model.addAttribute("msg", "登录失败，请检查用户和密码");
            return "login";
        }
    }


    @RequestMapping("/listUsers")
    public String listUsers(Model model, @RequestParam(defaultValue = "0") String currentPage){

        Page<Users> res;

        Pageable pageable = PageRequest.of(Integer.parseInt(currentPage), 15);

        Example<Users> example = Example.of(new Users());

        res = usersService.listUsers(example, pageable);

        model.addAttribute("userPage", res);
        return "index";
    }


    @RequestMapping(value = "/info")
    public String getInfo(Model model){

        Optional<Info> info = infoService.findById(1);

        info.ifPresent(i -> model.addAttribute("info", i));
        return "about";
    }


    @RequestMapping("/setInfo")
    public String setInfo(@RequestParam Integer infoId, @RequestParam String companyName,
                          @RequestParam  String tel, @RequestParam String email){
        infoService.update(new Info(companyName, tel, email).setInfoId(infoId));

        return "redirect:/admin/info";
    }
}
