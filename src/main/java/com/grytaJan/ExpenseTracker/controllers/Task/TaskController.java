package com.grytaJan.ExpenseTracker.controllers.Task;

import com.grytaJan.ExpenseTracker.controllers.Comment.dto.CommentDto;
import com.grytaJan.ExpenseTracker.controllers.Task.dto.CreateTaskDto;
import com.grytaJan.ExpenseTracker.controllers.Task.dto.TaskDto;
import com.grytaJan.ExpenseTracker.errors.ResourceNotFoundException;
import com.grytaJan.ExpenseTracker.models.Comment;
import com.grytaJan.ExpenseTracker.models.Project;
import com.grytaJan.ExpenseTracker.models.RoleConstants;
import com.grytaJan.ExpenseTracker.models.Task;
import com.grytaJan.ExpenseTracker.services.ProjectService;
import com.grytaJan.ExpenseTracker.services.TaskService;
import com.grytaJan.ExpenseTracker.utils.pagination.PageMapper;
import com.grytaJan.ExpenseTracker.utils.pagination.PageResponse;
import com.grytaJan.ExpenseTracker.utils.pagination.PaginationInfo;
import com.grytaJan.ExpenseTracker.utils.pagination.PaginationUtils;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
@CrossOrigin
public class TaskController {
    private final TaskService taskService;
    private final ProjectService projectService;

    @GetMapping
    @Secured({RoleConstants.ROLE_ADMIN, RoleConstants.ROLE_MANAGER})
    public ResponseEntity<PageResponse<TaskDto>> getAllTasks(
            @Valid @ModelAttribute PaginationInfo paginationInfo
    ) {
        Pageable pageable = PaginationUtils.createPageable(paginationInfo);

        Page<Task> tasks = taskService.getAllTasks(pageable);

        return new ResponseEntity<>(this.taskPageToPageResponse(tasks), HttpStatus.OK);
    }

    @GetMapping("/full")
    @Secured({RoleConstants.ROLE_ADMIN})
    public ResponseEntity<List<Task>> getAllTasksRaw() {
        List<Task> tasks = taskService.getAllTasks();
        return new ResponseEntity<>(tasks, HttpStatus.OK);

    }

    @GetMapping("/project/{id}")
    @Secured({RoleConstants.ROLE_ADMIN, RoleConstants.ROLE_MANAGER})
    public ResponseEntity<PageResponse<TaskDto>> getTasksOnProject(
            @PathVariable @Positive long id,
            @Valid @ModelAttribute PaginationInfo paginationInfo
    ) throws ResourceNotFoundException {

        Pageable pageable = PaginationUtils.createPageable(paginationInfo);


        Page<Task> tasks = projectService.getTasksOnProject(id, pageable);

        return new ResponseEntity<>(this.taskPageToPageResponse(tasks), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable @Positive Long id) throws ResourceNotFoundException {
        Task task = taskService.getTaskById(id);
        return new ResponseEntity<>(new TaskDto(task), HttpStatus.OK);
    }

    @PostMapping
    @Secured({RoleConstants.ROLE_ADMIN, RoleConstants.ROLE_MANAGER})
    public ResponseEntity<TaskDto> createTask(@RequestBody @Valid CreateTaskDto task) throws ResourceNotFoundException {
        Task newTask =  taskService.createTask(task);
        return new ResponseEntity<>(new TaskDto(newTask), HttpStatus.OK);
    }

    @PostMapping("/{id}/status")
    public ResponseEntity<TaskDto> changeStatusOnTask(@PathVariable @Positive long id, @RequestBody @Valid String status) throws ResourceNotFoundException {
        Task task = taskService.changeStatus(id, status);
        return new ResponseEntity<>(new TaskDto(task), HttpStatus.ACCEPTED);
    }

    @PostMapping("/{id}/addUsers")
    @Secured({RoleConstants.ROLE_ADMIN, RoleConstants.ROLE_MANAGER})
    public ResponseEntity<TaskDto> addUsersToTask(@PathVariable @Positive long id, @RequestBody @Valid Long userId) throws ResourceNotFoundException {
        Task task = taskService.addUsersToTask(id, userId);
        return new ResponseEntity<>(new TaskDto(task), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    @Secured({RoleConstants.ROLE_ADMIN, RoleConstants.ROLE_MANAGER})
    public ResponseEntity<String> deleteTask(@PathVariable @Positive long id) throws ResourceNotFoundException {
        taskService.deleteTask(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    private PageResponse<TaskDto> taskPageToPageResponse(Page<Task> tasks) {
        return PageMapper.pageToPageResponse(tasks, TaskDto::new);
    }
}
