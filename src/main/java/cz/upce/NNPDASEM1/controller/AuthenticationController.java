package cz.upce.NNPDASEM1.controller;

import cz.upce.NNPDASEM1.domain.user.User;
import cz.upce.NNPDASEM1.dto.AuthDto;
import cz.upce.NNPDASEM1.dto.PasswordDto;
import cz.upce.NNPDASEM1.dto.UserDto;
import cz.upce.NNPDASEM1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/auth")
@CrossOrigin
public class AuthenticationController extends ControllerAncestor {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> register(@RequestBody @Valid UserDto userDto) {
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
        userService.activateUser(token);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<AuthDto> login(@RequestBody @Valid AuthDto authDto) {
        String username = authDto.getUsername();
        String password = authDto.getPassword();
        String token = userService.login(username, password);
        User user = userService.findByUsername(username);

        AuthDto response = new AuthDto();
        response.setToken(token);
        response.setRole(user.getRole());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/me", method = RequestMethod.GET)
    public ResponseEntity<UserDto> me() {
        UserDto user = new UserDto(getCurrentUser());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "/reset-password-request", method = RequestMethod.POST)
    public ResponseEntity<?> resetPasswordRequest(@RequestBody AuthDto authDto) {
        userService.sendResetPasswordEmail(authDto.getUsername());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/reset-password/{token}", method = RequestMethod.POST)
    public ResponseEntity<?> resetPassword(@PathVariable("token") String token, @RequestBody @Valid PasswordDto passwordDto) {
        userService.resetPassword(token, passwordDto.getNewPassword());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
