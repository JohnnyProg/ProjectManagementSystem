package com.grytaJan.ExpenseTracker.services;

import com.grytaJan.ExpenseTracker.controllers.User.dto.UserLoginDTO;
import com.grytaJan.ExpenseTracker.controllers.User.dto.UserRegisterDTO;
import com.grytaJan.ExpenseTracker.errors.UserAlreadyExistsException;
import com.grytaJan.ExpenseTracker.models.Role;
import com.grytaJan.ExpenseTracker.models.User;
import com.grytaJan.ExpenseTracker.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public void register(UserRegisterDTO userRegisterDTO) throws UserAlreadyExistsException {
        Optional<User> existingUser = userRepository.findByUsernameOrEmail(userRegisterDTO.getUsername(), userRegisterDTO.getEmail());
        if (existingUser.isPresent()) {
            throw new UserAlreadyExistsException(userRegisterDTO.getEmail());
        }

        User user = new User();
        user.setEmail(userRegisterDTO.getEmail());
        user.setUsername(userRegisterDTO.getUsername());
        user.setPassword(encoder.encode((userRegisterDTO.getPassword())));
        user.setRoles(Collections.singletonList(Role.ROLE_USER));
        userRepository.save(user);
    }

    public String login(UserLoginDTO userLoginDTO) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userLoginDTO.getUsername(), userLoginDTO.getPassword());
        Authentication authentication =  authenticationManager.authenticate(token);

        if(authentication.isAuthenticated()) {
            User user = (User) authentication.getPrincipal();
            String jwt = jwtService.generateToken(user);
            return jwt;
        } else {
            return null;
        }

    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

}
