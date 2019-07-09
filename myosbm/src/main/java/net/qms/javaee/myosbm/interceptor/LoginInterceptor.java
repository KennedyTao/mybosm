package net.qms.javaee.myosbm.interceptor;

import net.qms.javaee.myosbm.service.AdminService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Resource
    private AdminService adminService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        Object ano = session.getAttribute("admin");

        if(ano != null){
            if(adminService.isExist((Integer)ano)){
                return true;
            }
        }

        response.sendRedirect("/admin/toLogin");
        return false;
    }
}
