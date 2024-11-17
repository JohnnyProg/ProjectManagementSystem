package com.grytaJan.ExpenseTracker.controllers.project.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateProjectDto {
    @NotNull(message = "Project name must not be null")
    @Size(min = 1, max = 100, message = "Project name must be between 1 and 100 characters")
    private String name;
    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;
}
