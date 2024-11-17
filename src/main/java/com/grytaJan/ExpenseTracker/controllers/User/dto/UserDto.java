package com.grytaJan.ExpenseTracker.controllers.User.dto;

import com.grytaJan.ExpenseTracker.controllers.Task.dto.TaskDto;
import com.grytaJan.ExpenseTracker.models.Role;
import com.grytaJan.ExpenseTracker.models.Task;
import com.grytaJan.ExpenseTracker.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private long id;
    private String username;
    private String password;
    private String email;
    private List<Role> roles;
    private List<TaskDto> tasks;
//    private List<CommentDto> comments;

    public UserDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.roles = user.getRoles();
        this.tasks = new ArrayList<>();
        for (Task task : user.getTasks()) {
            this.tasks.add(new TaskDto(task));
        }
    }
}
