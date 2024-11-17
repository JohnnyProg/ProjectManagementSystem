package com.grytaJan.ExpenseTracker.controllers.User.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserLoginDTO {
    @NotNull(message = "Username must not be null")
    private String username;

    @NotNull(message = "Password must not be null")
    private String password;
}