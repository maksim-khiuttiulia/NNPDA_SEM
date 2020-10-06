package cz.upce.NNPDASEM1.dto;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class PasswordDto {
    @Size(min = 8)
    private String newPassword;
    @Size(min = 8)
    private String oldPassword;
}
