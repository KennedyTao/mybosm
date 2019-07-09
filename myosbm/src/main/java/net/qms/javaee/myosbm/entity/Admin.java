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
@Table(name = "admin")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ano;

    @Column(name = "aname")
    private String aName;

    @Column(name = "apassword")
    private String aPassword;


    public Admin(String aName, String aPassword) {
        this.aName = aName;
        this.aPassword = aPassword;
    }
}
