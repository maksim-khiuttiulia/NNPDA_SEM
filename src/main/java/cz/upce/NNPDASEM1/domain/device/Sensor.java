package cz.upce.NNPDASEM1.domain.device;

import cz.upce.NNPDASEM1.domain.Measure;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "NN_SENSORS", uniqueConstraints = @UniqueConstraint(name = "UN_CONST_01", columnNames = {"DEVICE_ID", "NAME"}))
public class Sensor {
    @Id
    @Column(name = "SENSOR_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sensorId;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SensorType type;

    @ManyToOne
    @JoinColumn(name = "DEVICE_ID", nullable = false)
    private Device device;

    @OneToMany(mappedBy = "sensor")
    private List<Measure> measures;
}
