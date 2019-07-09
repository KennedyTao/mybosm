package net.qms.javaee.myosbm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class Lease {
    //未付款状态
    public static final Integer UNPAY = 0;
    //已付款状态
    public static final Integer PAIED = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer lno;

    private Integer bno;

    private Integer uno;

    @Column(name = "starttime")
    private Timestamp startTime;

    @Column(name = "endtime")
    private Timestamp endTime;

    @Column(name = "spendtime")
    private Long spendTime;

    private Double cost;

    @Column(name = "l_status")
    private Integer lStatus;


    public Lease(Integer bno, Integer uno, Timestamp startTime, Timestamp endTime, Long spendTime, Double cost, Integer lStatus) {
        this.bno = bno;
        this.uno = uno;
        this.startTime = startTime;
        this.endTime = endTime;
        this.spendTime = spendTime;
        this.cost = cost;
        this.lStatus = lStatus;
    }
}
