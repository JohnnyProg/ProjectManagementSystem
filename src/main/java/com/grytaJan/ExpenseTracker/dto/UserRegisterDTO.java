package com.grytaJan.ExpenseTracker.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserRegisterDTO {
    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private String email;
}
