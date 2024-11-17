package com.grytaJan.ExpenseTracker.controllers.Task.dto;

import com.grytaJan.ExpenseTracker.controllers.Comment.dto.CommentDto;
import com.grytaJan.ExpenseTracker.models.Task;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {
    private long id;
    private String title;
    private String description;
    private String priority;
    private String status;
    private String projectName;
    private List<String> userNames;
    private List<CommentDto> comments;

    public TaskDto(Task task) {
        this.id = task.getId();
        this.title = task.getTitle();
        this.description = task.getDescription();
        this.priority = task.getPriority().toString();
        this.status = task.getStatus().toString();
        this.projectName = task.getProject().getName();

        this.userNames = new ArrayList<>();
        this.comments = new ArrayList<>();
        if (task.getUsers() != null) {
            task.getUsers().forEach(user -> this.userNames.add(user.getUsername()));
        }
        if(task.getComments() != null) {
            task.getComments().forEach(comment -> this.comments.add(new CommentDto(comment)));
        }
    }
}
