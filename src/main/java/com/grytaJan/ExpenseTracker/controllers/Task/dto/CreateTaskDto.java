package com.grytaJan.ExpenseTracker.controllers.Task.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateTaskDto {
    @NotNull(message = "Task title must not be null")
    @Size(min = 1, max = 100, message = "Task title must be between 1 and 100 characters")
    private String title;

    @Size(max = 500, message = "Task description must not exceed 500 characters")
    private String description;

    @NotNull(message = "Priority must not be null")
    private String priority;

    @NotNull(message = "Project ID must not be null")
    private Long project_id;
}
