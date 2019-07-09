package net.qms.javaee.myosbm.dao;

import net.qms.javaee.myosbm.entity.Lease;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeaseDao extends JpaRepository<Lease, Integer> {

    List<Lease> findByUnoEquals(Integer uno);
}
