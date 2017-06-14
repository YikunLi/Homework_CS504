package analysis.domain;

import lombok.Data;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lykly on 2017/6/11.
 */
@Entity
@Table(name = "USER_INFOES")
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

    @OneToMany(mappedBy = "userInfo")
    private List<RunningInfo> runningInfoes = new ArrayList<RunningInfo>();

}
