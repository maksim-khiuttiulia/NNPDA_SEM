package cz.upce.NNPDASEM1.security;


import lombok.Data;

@Data
public class JwtToken {
    private String username;
    private JwtTokenType tokenType;
}
