package com.grytaJan.ExpenseTracker.controllers.User;

import com.grytaJan.ExpenseTracker.controllers.User.dto.UserDto;
import com.grytaJan.ExpenseTracker.controllers.User.dto.UserLoginDTO;
import com.grytaJan.ExpenseTracker.controllers.User.dto.UserRegisterDTO;
import com.grytaJan.ExpenseTracker.errors.UserAlreadyExistsException;
import com.grytaJan.ExpenseTracker.models.RoleConstants;
import com.grytaJan.ExpenseTracker.models.User;
import com.grytaJan.ExpenseTracker.services.UserService;
import com.grytaJan.ExpenseTracker.utils.pagination.PageMapper;
import com.grytaJan.ExpenseTracker.utils.pagination.PageResponse;
import com.grytaJan.ExpenseTracker.utils.pagination.PaginationInfo;
import com.grytaJan.ExpenseTracker.utils.pagination.PaginationUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public ResponseEntity<PageResponse<UserDto>> getAllUsers(
            @ModelAttribute @Valid PaginationInfo paginationInfo
    ) {

        Pageable pageable = PaginationUtils.createPageable(paginationInfo);

        Page<User> users = userService.getAllUsers(pageable);

        return new ResponseEntity<>(this.userPageToPageResponse(users), HttpStatus.OK);
    }

    private PageResponse<UserDto> userPageToPageResponse(Page<User> users) {
        return PageMapper.pageToPageResponse(users, UserDto::new);
    };
}
