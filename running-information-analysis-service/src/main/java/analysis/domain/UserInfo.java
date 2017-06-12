package analysis.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by lykly on 2017/6/11.
 */
@Entity
@Table(name = "USER_INFO")
@Data
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private long id;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "ADDRESS")
    private String address;

}
