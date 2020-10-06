package cz.upce.NNPDASEM1.domain.device;

import lombok.Data;

import javax.persistence.*;

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

    @ManyToOne
    @JoinColumn(name = "DEVICE_ID", nullable = false)
    private Device device;
}
