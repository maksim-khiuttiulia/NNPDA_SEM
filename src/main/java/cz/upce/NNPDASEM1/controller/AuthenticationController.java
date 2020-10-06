package cz.upce.NNPDASEM1.controller;

import cz.upce.NNPDASEM1.domain.user.User;
import cz.upce.NNPDASEM1.dto.AuthDto;
import cz.upce.NNPDASEM1.dto.UserDto;
import cz.upce.NNPDASEM1.exceptions.RestAuthenticationException;
import cz.upce.NNPDASEM1.exceptions.ValidationException;
import cz.upce.NNPDASEM1.security.JwtToken;
import cz.upce.NNPDASEM1.security.JwtTokenProvider;
import cz.upce.NNPDASEM1.security.JwtTokenType;
import cz.upce.NNPDASEM1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/auth")
public class AuthenticationController extends ControllerAncestor {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> register(@RequestBody UserDto userDto) {
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());
        userService.createNewUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/activate/{token}", method = RequestMethod.GET)
    public ResponseEntity<?> activateAccount(@PathVariable("token") String token) {
        JwtToken jwtToken = jwtTokenProvider.parseToken(token);
        if (jwtToken.getTokenType() != JwtTokenType.ACCOUNT_ACTIVATION) {
            throw new ValidationException("Wrong token type");
        }
        userService.activateUser(jwtToken.getUsername());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<AuthDto> login(@RequestBody AuthDto authDto) {
        String username = authDto.getUsername();
        String password = authDto.getPassword();
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        authenticationManager.authenticate(authenticationToken);
        User user = userService.findByUsername(username);
        if (user == null) {
            throw new RestAuthenticationException("User not found");
        }
        String token = jwtTokenProvider.createToken(username, JwtTokenType.NORMAL);
        AuthDto response = new AuthDto();
        response.setToken(token);
        response.setUsername(username);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
