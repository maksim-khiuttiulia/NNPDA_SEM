package cz.upce.NNPDASEM1.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.upce.NNPDASEM1.exceptions.RestAuthenticationException;
import cz.upce.NNPDASEM1.utils.DateUtils;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenProvider {
    private final String TOKEN_KEY_IN_CLAIMS = "token";

    @Autowired
    private JwtUserDetailService jwtUserDetailService;

    @Value("${jwt.token.secret}")
    private String secretToken;

    @Value("${jwt.token.expired}")
    private long expiredMillis;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @PostConstruct
    public void init() {
        secretToken = Base64.getEncoder().encodeToString(secretToken.getBytes());
    }

    public String createToken(String username, JwtTokenType tokenType) {
        JwtToken token = new JwtToken();
        token.setUsername(username);
        token.setTokenType(tokenType);
        Map<String, Object> claim = new HashMap<>();
        claim.put(TOKEN_KEY_IN_CLAIMS, token);

        Claims claims = Jwts.claims(claim);
        Date now = DateUtils.getCurrentDate();
        Date validity = new Date(now.getTime() + expiredMillis);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretToken)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        JwtToken jwtToken = parseToken(token);
        if (jwtToken.getTokenType() != JwtTokenType.NORMAL) {
            throw new RestAuthenticationException("Wrong token type");
        }
        UserDetails jwtUserDetails = jwtUserDetailService.loadUserByUsername(jwtToken.getUsername());
        return new UsernamePasswordAuthenticationToken(jwtUserDetails, "", null);
    }

    public JwtToken parseToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(secretToken).parseClaimsJws(token).getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(claims.get(TOKEN_KEY_IN_CLAIMS), JwtToken.class);
    }

    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretToken).parseClaimsJws(token);
            if (claimsJws.getBody().getExpiration().before(DateUtils.getCurrentDate())) {
                return false;
            }
            return true;
        } catch (JwtException | IllegalArgumentException ex) {
            throw new RestAuthenticationException("JWT Token is expired or invalid");
        }
    }
}
