package analysis.domain;

import lombok.Data;

import javax.persistence.*;

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
    private String latitude;

    @Column(name = "LONGITUDE")
    private String longitude;

    @Column(name = "RUNNING_DISTANCE")
    private double runningDistance;

    @Column(name = "TOTAL_RUNNING_TIME")
    private long totalRunningTime;

    @Column(name = "HEART_RATE")
    private int heartRate;

    @Column(name = "TIMESTAMP")
    private long timestamp;

    @JoinColumn(name = "USER_INFO_ID")
    @ManyToOne
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
