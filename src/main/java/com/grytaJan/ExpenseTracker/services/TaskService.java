package com.grytaJan.ExpenseTracker.services;

import com.grytaJan.ExpenseTracker.controllers.Task.dto.CreateTaskDto;
import com.grytaJan.ExpenseTracker.errors.ResourceNotFoundException;
import com.grytaJan.ExpenseTracker.models.*;
import com.grytaJan.ExpenseTracker.repositories.ProjectRepository;
import com.grytaJan.ExpenseTracker.repositories.TaskRepository;
import com.grytaJan.ExpenseTracker.repositories.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Page<Task> getAllTasks(Pageable pageable) {
        return taskRepository.findAll(pageable);
    }

    public Task getTaskById(Long id) throws ResourceNotFoundException {
        return taskRepository.findById(id).orElseThrow(() ->new ResourceNotFoundException("Task", Long.toString(id)));
    }

    public Task createTask(CreateTaskDto taskDto) throws ResourceNotFoundException {
        Project project = projectRepository.findById(taskDto.getProject_id()).orElseThrow(() -> new ResourceNotFoundException("Project", Long.toString(taskDto.getProject_id())));
        Task task = Task.builder()
                .title(taskDto.getTitle())
                .description(taskDto.getDescription())
                .priority(Priority.valueOf(taskDto.getPriority()))
                .status(Status.PENDING)
                .project(project)
                .build();
        return taskRepository.save(task);
    }

    public Task changeStatus(long id, String status) throws ResourceNotFoundException {
        Task task = taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task", Long.toString(id)));
        task.setStatus(Status.valueOf(status));
        return taskRepository.save(task);
    }

    public Task addUsersToTask(long taskId, Long userId) throws ResourceNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", Long.toString(userId)));

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task", Long.toString(taskId)));
        task.getUsers().add(user);
        user.getTasks().add(task);
        return taskRepository.save(task);
    }

    public void deleteTask(long id)  {
        taskRepository.deleteById(id);
    }

    public Task removeUserFromTask(Long taskId, Long userId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        task.getUsers().remove(user);
        user.getTasks().remove(task);

        return taskRepository.save(task);
    }

}
