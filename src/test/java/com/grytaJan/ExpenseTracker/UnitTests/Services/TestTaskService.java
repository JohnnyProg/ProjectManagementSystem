package com.grytaJan.ExpenseTracker.UnitTests.Services;
import com.grytaJan.ExpenseTracker.controllers.Task.dto.CreateTaskDto;
import com.grytaJan.ExpenseTracker.controllers.project.dto.CreateProjectDto;
import com.grytaJan.ExpenseTracker.errors.ResourceNotFoundException;
import com.grytaJan.ExpenseTracker.models.*;
import com.grytaJan.ExpenseTracker.repositories.CommentRepository;
import com.grytaJan.ExpenseTracker.repositories.ProjectRepository;
import com.grytaJan.ExpenseTracker.repositories.TaskRepository;
import com.grytaJan.ExpenseTracker.repositories.UserRepository;
import com.grytaJan.ExpenseTracker.services.CommentService;
import com.grytaJan.ExpenseTracker.services.TaskService;

import com.grytaJan.ExpenseTracker.services.ProjectService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Add this annotation
public class TestTaskService {
    @Mock
    private TaskRepository taskRepository;
    @Mock
    private ProjectRepository projectRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    void testGetAllTasks() {
        List<Task> mockTasks = List.of(new Task(), new Task());
        when(taskRepository.findAll()).thenReturn(mockTasks);

        List<Task> allTasks = taskService.getAllTasks();

        assertEquals(mockTasks, allTasks);
        verify(taskRepository).findAll();
    }

    @Test
    void testGetTaskById_Success() throws ResourceNotFoundException {
        long taskId = 1L;
        Task mockTask = new Task();
        mockTask.setId(taskId);
        Optional<Task> optionalTask = Optional.of(mockTask);

        when(taskRepository.findById(taskId)).thenReturn(optionalTask);

        Task task = taskService.getTaskById(taskId);

        assertEquals(mockTask, task);
        verify(taskRepository).findById(taskId);
    }

    @Test
    void testGetTaskById_NotFound() throws ResourceNotFoundException {
        long taskId = 1L;

        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> taskService.getTaskById(taskId));
        verify(taskRepository).findById(taskId);
    }

    @Test
    void testCreateTask() throws ResourceNotFoundException {
        CreateTaskDto taskDto = new CreateTaskDto("Test Task", "This is a test task", "HIGH", 1L);
        Project project = new Project();
        project.setId(taskDto.getProject_id());
        Optional<Project> optionalProject = Optional.of(project);

        when(projectRepository.findById(taskDto.getProject_id())).thenReturn(optionalProject);
        when(taskRepository.save(any(Task.class))).thenReturn(new Task()); // Avoid creating specific task object

        Task createdTask = taskService.createTask(taskDto);

        assertNotNull(createdTask);
        verify(projectRepository).findById(taskDto.getProject_id());
        verify(taskRepository).save(any(Task.class)); // Adjusted verification for flexibility
    }

    @Test
    void testChangeStatus_Success() throws ResourceNotFoundException {
        long taskId = 1L;
        Status newStatus = Status.IN_PROGRESS;
        Task mockTask = Task.builder()
                .id(taskId)
                .status(Status.PENDING)
                .build();

        Task mockTaskResult = Task.builder()
                .id(taskId)
                .status(newStatus)
                .build();
        Optional<Task> optionalTask = Optional.of(mockTask);

        when(taskRepository.findById(taskId)).thenReturn(optionalTask);
        when(taskRepository.save(mockTaskResult)).thenReturn(mockTaskResult);
        Task res = taskService.changeStatus(taskId, newStatus.name());
        assertEquals(res, mockTaskResult);
        verify(taskRepository).save(mockTask);
    }
}
