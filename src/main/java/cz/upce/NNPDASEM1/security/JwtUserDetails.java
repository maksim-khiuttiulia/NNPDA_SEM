package cz.upce.NNPDASEM1.security;

import cz.upce.NNPDASEM1.domain.user.UserRole;
import cz.upce.NNPDASEM1.domain.user.UserStatus;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data
public class JwtUserDetails implements UserDetails {

    private Long userId;
    private String username;
    private String email;
    private String password;
    private Date lastPasswordReset;
    private String firstName;
    private String lastName;
    private UserStatus status;
    private UserRole role;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<UserRole> rights = new ArrayList<>();
        rights.add(role);
        return rights;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return status == UserStatus.ACTIVE;
    }

    @Override
    public boolean isAccountNonLocked() {
        return status == UserStatus.ACTIVE;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return status == UserStatus.ACTIVE;
    }

    @Override
    public boolean isEnabled() {
        return status == UserStatus.ACTIVE;
    }
}
