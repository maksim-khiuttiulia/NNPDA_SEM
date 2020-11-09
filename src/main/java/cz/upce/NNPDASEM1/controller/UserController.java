package cz.upce.NNPDASEM1.controller;

import cz.upce.NNPDASEM1.domain.user.User;
import cz.upce.NNPDASEM1.dto.PasswordDto;
import cz.upce.NNPDASEM1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/users")
@CrossOrigin
public class UserController extends ControllerAncestor {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/change-password", method = RequestMethod.POST)
    public ResponseEntity<?> updatePassword(@RequestBody @Valid PasswordDto passwordDto) {
        User user = getCurrentUser();
        userService.changePassword(user, passwordDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
