package com.grytaJan.ExpenseTracker.controllers.Comment.dto;

import lombok.Data;

@Data
public class CreateCommentDto {
    private String content;
    private long taskId;
}
