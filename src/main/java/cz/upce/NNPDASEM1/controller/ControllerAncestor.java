package cz.upce.NNPDASEM1.controller;


import cz.upce.NNPDASEM1.domain.user.User;
import cz.upce.NNPDASEM1.exceptions.RestAuthenticationException;
import cz.upce.NNPDASEM1.security.JwtToken;
import cz.upce.NNPDASEM1.security.JwtTokenProvider;
import cz.upce.NNPDASEM1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;

public class ControllerAncestor {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenProvider tokenProvider;

    protected User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        return userService.findByUsername(currentUserName);
    }

    protected JwtToken getCurrentToken(HttpServletRequest request) {
        String token = tokenProvider.resolveToken(request);
        if (token == null) {
            throw new RestAuthenticationException("Token invalid");
        }
        return tokenProvider.parseToken(token);
    }
}
