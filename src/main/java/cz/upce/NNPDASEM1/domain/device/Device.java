package cz.upce.NNPDASEM1.domain.device;

import cz.upce.NNPDASEM1.domain.user.User;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "NN_DEVICES", uniqueConstraints = @UniqueConstraint(name = "UN_CONST_NN_DEVICES_01", columnNames = {"USER_ID", "NAME"}))
public class Device {
    @Id
    @Column(name = "DEVICE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deviceId;

    @Column(name = "NAME", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User owner;

    @OneToMany(mappedBy = "device")
    private List<Sensor> sensors;
}
