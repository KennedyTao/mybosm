package net.qms.javaee.myosbm.dao;

import net.qms.javaee.myosbm.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminDao extends JpaRepository<Admin, Integer> {
    Optional<Admin> findByANameAndAPassword(String aname, String apassword);

    Boolean existsByAno(Integer ano);

}
