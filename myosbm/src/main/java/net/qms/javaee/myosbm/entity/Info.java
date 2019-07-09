package net.qms.javaee.myosbm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "info")
public class Info {

    @Id
    @Column(name = "info_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer infoId;

    @Column(name = "company_name")
    private String companyName;

    private String tel;

    private String email;


    public Info(String companyName, String tel, String email) {
        this.companyName = companyName;
        this.tel = tel;
        this.email = email;
    }
}
