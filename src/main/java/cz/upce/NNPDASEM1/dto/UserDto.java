package cz.upce.NNPDASEM1.dto;


import cz.upce.NNPDASEM1.domain.user.User;
import cz.upce.NNPDASEM1.domain.user.UserRole;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class UserDto {
    private Long id;

    @NotBlank
    private String username;

    @Email
    @NotBlank
    private String email;

    @Size(min = 8)
    private String password;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private UserRole role;

    private List<LocationDto> locations;

    public UserDto() {
    }

    public UserDto(User user) {
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.role = user.getRole();
        this.locations = user.getLocations().stream().map(LocationDto::new).collect(Collectors.toList());
    }
}
