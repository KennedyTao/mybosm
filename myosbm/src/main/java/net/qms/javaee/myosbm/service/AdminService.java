package net.qms.javaee.myosbm.service;

import net.qms.javaee.myosbm.dao.AdminDao;
import net.qms.javaee.myosbm.entity.Admin;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

@Service
public class AdminService {
    @Resource
    private AdminDao adminDao;


    public Optional<Admin> Login(String name, String password){
        return adminDao.findByANameAndAPassword(name, password);
    }


    public Boolean isExist(Integer ano){
        return adminDao.existsByAno(ano);
    }
}
