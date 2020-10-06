package cz.upce.NNPDASEM1.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
    private String username;

    private String email;

    private String password;

    private String firstName;

    private String lastName;
}
