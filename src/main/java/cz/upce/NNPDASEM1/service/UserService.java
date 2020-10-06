package cz.upce.NNPDASEM1.service;

import cz.upce.NNPDASEM1.domain.user.User;
import cz.upce.NNPDASEM1.domain.user.UserStatus;
import cz.upce.NNPDASEM1.exceptions.ValidationException;
import cz.upce.NNPDASEM1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void activateUser(String username) {
        User user = findByUsername(username);
        if (user == null) {
            throw new ValidationException("User " + username + " not exits");
        }
        if (user.getStatus() != UserStatus.NEW_USER) {
            throw new ValidationException("User " + username + " already activated");
        }
        user.setStatus(UserStatus.ACTIVE);
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
}
