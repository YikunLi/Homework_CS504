package analysis.domain;

import lombok.Data;
import org.springframework.context.annotation.Primary;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by lykly on 2017/6/11.
 */
@Entity
@Table(name = "RUNNING_INFO")
@Data
public class RunningInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    @Column(name = "RUNNING_ID")
    private String runningId;

    @Column(name = "LATITUDE")
    private double latitude;

    @Column(name = "LONGITUDE")
    private double longitude;

    @Column(name = "RUNNING_DISTANCE")
    private double runningDistance;

    @Column(name = "TOTAL_RUNNING_TIME")
    private double totalRunningTime;

    @Column(name = "HEART_RATE")
    private int heartRate;

    @Column(name = "TIMESTAMP")
    private Date timestamp;

    @JoinColumn(name = "USER_INFO_ID")
    @ManyToOne(cascade = CascadeType.ALL)
    private UserInfo userInfo;

    private static final String HEALTH_WARNING_LEVEL_HIGH = "HIGH";
    private static final String HEALTH_WARNING_LEVEL_NORMAL = "NORMAL";
    private static final String HEALTH_WARNING_LEVEL_LOW = "LOW";

    public String getHealthWarningLevel() {
        if (this.heartRate > 120) {
            return HEALTH_WARNING_LEVEL_HIGH;
        } else if (this.heartRate > 75) {
            return HEALTH_WARNING_LEVEL_NORMAL;
        } else {
            return HEALTH_WARNING_LEVEL_LOW;
        }
    }
}
