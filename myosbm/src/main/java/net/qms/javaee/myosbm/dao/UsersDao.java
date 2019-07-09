package net.qms.javaee.myosbm.dao;

import net.qms.javaee.myosbm.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UsersDao extends JpaRepository<Users, Integer> {

    Optional<Users> findByTel(String tel);


    Optional<Users> findByOpenId(String openId);

    @Modifying
    @Query("update Users user set user.sessionKey= :sessionKey where user.openId= :openId")
    void updateSessionKeyByOpenId(@Param("openId") String openId, @Param("sessionKey") String sessionKey);


    Optional<Users> findBySessionKey(String sessionKey);
}
