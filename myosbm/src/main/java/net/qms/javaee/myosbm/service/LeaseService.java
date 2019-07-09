package net.qms.javaee.myosbm.service;

import net.qms.javaee.myosbm.dao.LeaseDao;
import net.qms.javaee.myosbm.entity.Lease;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LeaseService {
    @Resource
    private LeaseDao leaseDao;


    public Lease add(Lease lease){
        return leaseDao.save(lease);
    }


    public Optional<Lease> findByLno(Integer lno){
        return leaseDao.findById(lno);
    }


    public Lease update(Lease lease){
        return leaseDao.saveAndFlush(lease);
    }


    public Page<Lease> listByPage(Example<Lease> example, Pageable pageable){
        return leaseDao.findAll(example, pageable);
    }


    public List<Lease> listByUser(Integer uno){
        return leaseDao.findByUnoEquals(uno).stream().sorted(Comparator.comparingInt(Lease::getLno).reversed()).collect(Collectors.toList());
    }
}
