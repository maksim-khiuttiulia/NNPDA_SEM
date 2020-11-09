package cz.upce.NNPDASEM1.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import cz.upce.NNPDASEM1.domain.user.UserRole;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthDto {
    @NotBlank
    private String username;
    @Size(min = 8)
    private String password;
    private String token;
    private UserRole role;
}
