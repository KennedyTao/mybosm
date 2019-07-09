package net.qms.javaee.myosbm.service;

import net.qms.javaee.myosbm.dao.BikeDao;
import net.qms.javaee.myosbm.entity.Bike;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class BikeService {
    @Resource
    private BikeDao bikeDao;


    public List<Bike> listAllBy(Example<Bike> example){
        return bikeDao.findAll(example);
    }


    public Bike addBike(Bike bike){
        return bikeDao.save(bike);
    }


    public void delete(Integer bno){
        bikeDao.deleteById(bno);
    }


    public Optional<Bike> findById(Integer bno){
        return bikeDao.findById(bno);
    }


    public List<Bike> findBikeIn(BigDecimal log, BigDecimal lat){
        //范围约为中心500米
        BigDecimal range = BigDecimal.valueOf(0.005);

        BigDecimal startLog = log.subtract(range);
        BigDecimal endLog = log.add(range);
        BigDecimal startLat = lat.subtract(range);
        BigDecimal endLat = lat.add(range);

        handleBigDecimal(startLog, startLat, endLat, endLog);

//        System.out.println(startLog);
//        System.out.println(endLog);
//        System.out.println(startLat);
//        System.out.println(endLat);

        return bikeDao.findByLatitudeBetweenAndLongitudeBetweenAndBStatusEquals(startLat, endLat, startLog, endLog, Bike.BIKESTATUS_NORMAL);
    }


    private void handleBigDecimal(BigDecimal... bigDecimals){
        for (BigDecimal num : bigDecimals) {
            if (num.compareTo(BigDecimal.valueOf(0)) < 0){
                num = BigDecimal.valueOf(0);
//                //上限暂时不计算
            }
        }
    }

    public Boolean isExist(Integer bno){
        return bikeDao.existsById(bno);
    }

    public Boolean unLock(Integer bno){
        return changeBikeStatus(bno, Bike.BIKESTATUS_USING);
    }


    public Boolean lock(Integer bno){
        return changeBikeStatus(bno, Bike.BIKESTATUS_NORMAL);
    }


    private Boolean changeBikeStatus(Integer bno, Integer status){
        Optional<Bike> bike = bikeDao.findById(bno);

        if (bike.isPresent()){

            Bike bike1 = bike.get();

            if(bike1.getBStatus().equals(status)){
                return false;
            }

            bike1.setBStatus(status);

            bikeDao.saveAndFlush(bike1);
            return true;

        }else {
            return false;
        }
    }


    public Bike changeLocation(BigDecimal log, BigDecimal lat, Integer bno){
        Optional<Bike> bikeOptional = findById(bno);

        if (bikeOptional.isPresent()){
            Bike bike = bikeOptional.get();

            bike.setLatitude(lat);
            bike.setLongitude(log);
            Bike bike1 = bikeDao.saveAndFlush(bike);

            if (bike1 != null){
                return bike1;

            }else {
                return null;
            }


        }else {
            return null;
        }
    }

}
