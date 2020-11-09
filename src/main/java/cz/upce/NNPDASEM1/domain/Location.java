package cz.upce.NNPDASEM1.domain;

import cz.upce.NNPDASEM1.domain.user.User;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "NN_LOCATIONS")
public class Location {
    @Id
    @Column(name = "LOCATION_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long locationId;

    @Column(nullable = false)
    private String alias;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private String buildingNumber;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;
}
