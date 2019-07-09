package net.qms.javaee.myosbm.dao;

import net.qms.javaee.myosbm.entity.Bike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface BikeDao extends JpaRepository<Bike, Integer> {

    List<Bike> findByLatitudeBetweenAndLongitudeBetweenAndBStatusEquals(BigDecimal startLat, BigDecimal endLat,
                                                                        BigDecimal startLongitude, BigDecimal endLongitude,
                                                                        Integer bStatus);

}
