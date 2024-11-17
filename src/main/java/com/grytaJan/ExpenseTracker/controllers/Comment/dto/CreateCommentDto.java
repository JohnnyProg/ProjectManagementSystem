package com.grytaJan.ExpenseTracker.controllers.Comment.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateCommentDto {
    @NotNull(message = "Content must not be null")
    @Size(min = 1, max = 500, message = "Content must be between 1 and 500 characters")
    private String content;
    @NotNull(message = "Task ID must not be null")
    private long taskId;
}
