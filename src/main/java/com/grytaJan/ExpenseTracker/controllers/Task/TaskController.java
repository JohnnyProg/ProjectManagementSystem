package com.grytaJan.ExpenseTracker.controllers.Task;

import com.grytaJan.ExpenseTracker.controllers.Task.dto.CreateTaskDto;
import com.grytaJan.ExpenseTracker.controllers.Task.dto.TaskDto;
import com.grytaJan.ExpenseTracker.errors.ResourceNotFoundException;
import com.grytaJan.ExpenseTracker.models.Project;
import com.grytaJan.ExpenseTracker.models.RoleConstants;
import com.grytaJan.ExpenseTracker.models.Task;
import com.grytaJan.ExpenseTracker.services.ProjectService;
import com.grytaJan.ExpenseTracker.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;
    private final ProjectService projectService;

    @GetMapping
    @Secured({RoleConstants.ROLE_ADMIN, RoleConstants.ROLE_MANAGER})
    public ResponseEntity<List<TaskDto>> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        List<TaskDto> dtos = new ArrayList<>();
        tasks.forEach(task -> {
            System.out.println("Task = ");
            System.out.println(task);
            dtos.add(new TaskDto(task));
        });
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping("/full")
    @Secured({RoleConstants.ROLE_ADMIN})
    public ResponseEntity<List<Task>> getAllTasksRaw() {
        List<Task> tasks = taskService.getAllTasks();
        return new ResponseEntity<>(tasks, HttpStatus.OK);

    }

    @GetMapping("/project/{id}")
    @Secured({RoleConstants.ROLE_ADMIN, RoleConstants.ROLE_MANAGER})
    public ResponseEntity<List<TaskDto>> getTasksOnProject(@PathVariable long id) throws ResourceNotFoundException {
        List<Task> tasks = projectService.getTasksOnProject(id);
        List<TaskDto> dtos = new ArrayList<>();
        tasks.forEach(task -> {
            dtos.add(new TaskDto(task));
        });
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable Long id) throws ResourceNotFoundException {
        Task task = taskService.getTaskById(id);
        return new ResponseEntity<>(new TaskDto(task), HttpStatus.OK);
    }

    @PostMapping
    @Secured({RoleConstants.ROLE_ADMIN, RoleConstants.ROLE_MANAGER})
    public ResponseEntity<TaskDto> createTask(@RequestBody CreateTaskDto task) throws ResourceNotFoundException {
        Task newTask =  taskService.createTask(task);
        return new ResponseEntity<>(new TaskDto(newTask), HttpStatus.OK);
    }

    @PostMapping("/{id}/status")
    public ResponseEntity<TaskDto> changeStatusOnTask(@PathVariable long id, @RequestBody String status) throws ResourceNotFoundException {
        Task task = taskService.changeStatus(id, status);
        return new ResponseEntity<>(new TaskDto(task), HttpStatus.ACCEPTED);
    }

    @PostMapping("/{id}/addUsers")
    @Secured({RoleConstants.ROLE_ADMIN, RoleConstants.ROLE_MANAGER})
    public ResponseEntity<TaskDto> addUsersToTask(@PathVariable long id, @RequestBody Long userId) throws ResourceNotFoundException {
        Task task = taskService.addUsersToTask(id, userId);
        return new ResponseEntity<>(new TaskDto(task), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    @Secured({RoleConstants.ROLE_ADMIN, RoleConstants.ROLE_MANAGER})
    public ResponseEntity<String> deleteTask(@PathVariable long id) throws ResourceNotFoundException {
        taskService.deleteTask(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
