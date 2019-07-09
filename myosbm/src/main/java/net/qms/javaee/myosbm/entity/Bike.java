package net.qms.javaee.myosbm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "bike")
public class Bike {

    //单车状态：正常
    public static final Integer BIKESTATUS_NORMAL = 0;

    //单车状态：使用中
    public static final Integer BIKESTATUS_USING = 1;

    //单车状态：维护中
    public static final Integer BIKESTATUS_MAINTAIN = 2;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bno;

    @Column(name = "bstatus")
    private Integer bStatus;

    //单车的经度(东经)
//    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal latitude;

    //单车的纬度(北纬)
//    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal longitude;


    public Bike(Integer bStatus, BigDecimal latitude, BigDecimal longitude) {
        this.bStatus = bStatus;
        this.latitude = latitude;
        this.longitude = longitude;
    }

}
