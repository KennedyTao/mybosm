package net.qms.javaee.myosbm.service;

import net.qms.javaee.myosbm.dao.InfoDao;
import net.qms.javaee.myosbm.entity.Info;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

@Service
public class InfoService {
    @Resource
    private InfoDao infoDao;


    public Optional<Info> findById(Integer id){
        return infoDao.findById(id);
    }


    public Info update(Info info){
        return infoDao.saveAndFlush(info);
    }
}
