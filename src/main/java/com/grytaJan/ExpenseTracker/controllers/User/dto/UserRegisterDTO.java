package com.grytaJan.ExpenseTracker.controllers.User.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRegisterDTO {
    @NotNull(message = "Username must not be null")
    @Size(min = 3, max = 30, message = "Username must be between 3 and 30 characters")
    private String username;

    @NotNull(message = "Password must not be null")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

    @NotNull(message = "Email must not be null")
    @Email(message = "Invalid email format")
    private String email;
}
