package com.grytaJan.ExpenseTracker.controllers.User;

import com.grytaJan.ExpenseTracker.controllers.User.dto.UserDto;
import com.grytaJan.ExpenseTracker.controllers.User.dto.UserLoginDTO;
import com.grytaJan.ExpenseTracker.controllers.User.dto.UserRegisterDTO;
import com.grytaJan.ExpenseTracker.errors.UserAlreadyExistsException;
import com.grytaJan.ExpenseTracker.models.RoleConstants;
import com.grytaJan.ExpenseTracker.models.User;
import com.grytaJan.ExpenseTracker.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRegisterDTO user) throws UserAlreadyExistsException {
        userService.register(user);
        return new ResponseEntity<>("User created", HttpStatus.ACCEPTED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody UserLoginDTO user){
        String token = userService.login(user);
        if(token != null) {
            return new ResponseEntity<>(token, HttpStatus.OK);
        }
        return new ResponseEntity<>("wrong login or password", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/getAll")
    @Secured({RoleConstants.ROLE_ADMIN})
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        List<UserDto> dtos = new ArrayList<>();
        for(User user : users) {
            dtos.add(new UserDto(user));
        }
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }
}
