package cz.upce.NNPDASEM1.service;

import cz.upce.NNPDASEM1.domain.user.User;
import cz.upce.NNPDASEM1.domain.user.UserStatus;
import cz.upce.NNPDASEM1.dto.PasswordDto;
import cz.upce.NNPDASEM1.exceptions.RestAuthenticationException;
import cz.upce.NNPDASEM1.exceptions.ValidationException;
import cz.upce.NNPDASEM1.repository.UserRepository;
import cz.upce.NNPDASEM1.security.JwtToken;
import cz.upce.NNPDASEM1.security.JwtTokenProvider;
import cz.upce.NNPDASEM1.security.JwtTokenType;
import cz.upce.NNPDASEM1.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private BCryptPasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        passwordEncoder = new BCryptPasswordEncoder();
    }

    @Transactional(rollbackOn = Exception.class)
    public User createNewUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setStatus(UserStatus.NEW_USER);
        User newUser = userRepository.save(user);
        emailService.sendRegisterEmail(newUser);
        return newUser;
    }

    @Transactional(rollbackOn = Exception.class)
    public void activateUser(String token) {
        User user = getUserByToken(token, JwtTokenType.ACCOUNT_ACTIVATION);
        if (user.getStatus() != UserStatus.NEW_USER) {
            throw new ValidationException("User " + user.getUsername() + " already activated");
        }
        user.setStatus(UserStatus.ACTIVE);
        userRepository.save(user);
    }

    public String login(String username, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        authenticationManager.authenticate(authenticationToken);
        User user = findByUsername(username);
        if (user == null) {
            throw new RestAuthenticationException("User not found");
        }
        return jwtTokenProvider.createToken(username, JwtTokenType.NORMAL);
    }


    public void sendResetPasswordEmail(String username) {
        User user = findByUsername(username);
        if (user == null) {
            throw new ValidationException("User " + username + " not exits");
        }
        if (user.getStatus() != UserStatus.ACTIVE) {
            throw new ValidationException("User " + username + " not active");
        }
        emailService.sendResetPasswordEmail(user);
    }

    @Transactional(rollbackOn = Exception.class)
    public void resetPassword(String token, String password) {
        User user = getUserByToken(token, JwtTokenType.PASSWORD_RESET);
        user.setPassword(passwordEncoder.encode(password));
        user.setLastPasswordReset(DateUtils.getCurrentDate());
        userRepository.save(user);
    }

    @Transactional(rollbackOn = Exception.class)
    public void changePassword(User user, PasswordDto passwordDto) {
        if (!passwordEncoder.matches(passwordDto.getOldPassword(), user.getPassword())) {
            throw new ValidationException("Passwords not equals");
        }
        user.setPassword(passwordEncoder.encode(passwordDto.getNewPassword()));
        user.setLastPasswordReset(DateUtils.getCurrentDate());
        userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User update(Long userId, User user) {
        return null;
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    private User getUserByToken(String token, JwtTokenType tokenType) {
        JwtToken jwtToken = jwtTokenProvider.parseToken(token);
        if (jwtToken.getTokenType() != tokenType) {
            throw new ValidationException("Wrong token type");
        }
        String username = jwtToken.getUsername();
        User user = findByUsername(username);
        if (user == null) {
            throw new ValidationException("User " + username + " not exits");
        }
        return user;
    }
}
