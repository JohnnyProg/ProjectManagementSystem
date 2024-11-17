package com.grytaJan.ExpenseTracker.controllers.Task.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateTaskDto {
    private String title;
    private String description;
    private String priority;
    private Long project_id;
}
