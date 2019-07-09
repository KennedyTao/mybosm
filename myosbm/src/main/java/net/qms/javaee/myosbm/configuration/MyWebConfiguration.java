package net.qms.javaee.myosbm.configuration;

import net.qms.javaee.myosbm.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class MyWebConfiguration implements WebMvcConfigurer {
    @Resource
    private LoginInterceptor loginInterceptor;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration interceptorRegistration = registry.addInterceptor(loginInterceptor);
        interceptorRegistration.addPathPatterns("/admin/**")
                            .addPathPatterns("/lease/toInsufficientLease")
                            .addPathPatterns("/lease/toLeaseInfo")
                            .addPathPatterns("/bike/toAdd")
                            .addPathPatterns("/bike/add")
                            .addPathPatterns("/bike/del")
                            .excludePathPatterns("/admin/login")
                            .excludePathPatterns("/admin/toLogin")
                            .excludePathPatterns("/admin/index");
    }
}
