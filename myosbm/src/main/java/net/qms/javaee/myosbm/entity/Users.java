package net.qms.javaee.myosbm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
@Table(name = "Users")
public class Users {
    //用户状态：未缴纳押金
    public static final Integer USER_NOT_PAIED = 0;
    //用户状态：正常
    public static final Integer USER_NORMAL = 1;
    //用户状态：冻结
    public static final Integer USER_FORZEN = 2;

    public static final Double USER_RENT = 199d;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer uno;

    @Column(name = "open_id")
    private String openId;

    @Column(name = "session_key")
    private String sessionKey;

    private String tel;

    private String username;

    @Column(name = "ustatus")
    private Integer uStatus;

    private Double rent;


    public Users(String openId, String sessionKey, String tel, String username, Integer uStatus, Double rent) {
        this.openId = openId;
        this.sessionKey = sessionKey;
        this.tel = tel;
        this.username = username;
        this.uStatus = uStatus;
        this.rent = rent;
    }
}
