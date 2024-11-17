package com.grytaJan.ExpenseTracker.controllers.project.dto;

import com.grytaJan.ExpenseTracker.controllers.Task.dto.TaskDto;
import com.grytaJan.ExpenseTracker.models.Project;
import com.grytaJan.ExpenseTracker.models.Task;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class ProjectDto {
    private Long id;
    private String name;
    private String description;
    private List<TaskDto> tasks;

    public ProjectDto(Project project) {
        this.id = project.getId();
        this.name = project.getName();
        this.description = project.getDescription();
        this.tasks = new ArrayList<>();
        if(project.getTasks() != null) {
            project.getTasks().forEach(task -> {
                this.tasks.add(new TaskDto(task));
            });
        }
    }
}
