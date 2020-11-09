package cz.upce.NNPDASEM1.domain;

import cz.upce.NNPDASEM1.domain.device.Sensor;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "NN_MEASURES")
public class Measure {
    @Id
    @Column(name = "MEASURE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long measureId;

    @Column(nullable = false)
    @CreatedDate
    private Date date;

    @Column(nullable = false)
    private Double value;

    @ManyToOne
    @JoinColumn(name = "SENSOR_ID", nullable = false)
    private Sensor sensor;
}
