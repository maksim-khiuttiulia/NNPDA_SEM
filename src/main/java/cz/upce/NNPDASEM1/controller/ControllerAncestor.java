package cz.upce.NNPDASEM1.controller;


import cz.upce.NNPDASEM1.domain.user.User;
import cz.upce.NNPDASEM1.exceptions.ValidationException;
import cz.upce.NNPDASEM1.security.JwtTokenProvider;
import cz.upce.NNPDASEM1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class ControllerAncestor {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenProvider tokenProvider;

    protected User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        User user = userService.findByUsername(currentUserName);
        if (user == null) {
            throw new ValidationException("User not exists");
        }
        return user;
    }

}
