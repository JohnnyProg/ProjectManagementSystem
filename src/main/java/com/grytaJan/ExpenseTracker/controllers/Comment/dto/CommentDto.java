package com.grytaJan.ExpenseTracker.controllers.Comment.dto;

import com.grytaJan.ExpenseTracker.models.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class CommentDto {
    private String content;
    private String taskTitle;
    private String userName;
    
    public CommentDto(Comment comment) {
        this.content = comment.getContent();
        this.taskTitle = comment.getTask().getTitle();
        this.userName = comment.getUser().getUsername();
    }
}
