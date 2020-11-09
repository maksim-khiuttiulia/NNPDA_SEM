package cz.upce.NNPDASEM1.domain.user;

import cz.upce.NNPDASEM1.domain.Location;
import cz.upce.NNPDASEM1.domain.device.Device;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Data
@Entity
@Table(name = "NN_USERS")
public class User {
    @Id
    @Column(name = "USER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, unique = true)
    private String username;

    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = true, name = "LAST_PASSWORD_RESET")
    private Date lastPasswordReset;

    @Column(nullable = false, name = "FIRST_NAME")
    private String firstName;

    @Column(nullable = false, name = "LAST_NAME")
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "STATUS")
    private UserStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "ROLE")
    private UserRole role;

    @OneToMany(mappedBy = "owner")
    private List<Device> devices;

    @OneToMany(mappedBy = "user")
    private List<Location> locations;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId.equals(user.userId) &&
                username.equals(user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, username);
    }
}
