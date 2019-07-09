package net.qms.javaee.myosbm.dao;

import net.qms.javaee.myosbm.entity.Info;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InfoDao extends JpaRepository<Info, Integer> {
}
