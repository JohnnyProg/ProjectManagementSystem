package com.grytaJan.ExpenseTracker.services;

import com.grytaJan.ExpenseTracker.controllers.project.dto.CreateProjectDto;
import com.grytaJan.ExpenseTracker.errors.ResourceNotFoundException;
import com.grytaJan.ExpenseTracker.models.Project;
import com.grytaJan.ExpenseTracker.models.Task;
import com.grytaJan.ExpenseTracker.repositories.ProjectRepository;
import com.grytaJan.ExpenseTracker.repositories.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Page<Project> getAllProjects(Pageable pageable) {
        return projectRepository.findAll(pageable);
    }

    public Project getProjectById(Long id) throws ResourceNotFoundException {
        return projectRepository.findById(id).orElseThrow( () -> new ResourceNotFoundException("Project", Long.toString(id)));
    }

    public Project createProject(CreateProjectDto projectDto) {
        Project project = Project.builder()
                .name(projectDto.getName())
                .description(projectDto.getDescription())
                .tasks(new ArrayList<>())
                .build();
        return projectRepository.save(project);
    }

    public Project updateProject(Long id, Project projectDetails) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        project.setName(projectDetails.getName());
        project.setDescription(projectDetails.getDescription());
        return projectRepository.save(project);
    }

    public Page<Task> getTasksOnProject(long id, Pageable pageable) throws ResourceNotFoundException {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project", Long.toString(id)));
        Page<Task> tasks = taskRepository.findAllByProjectId(id, pageable);
        return tasks;
    }

    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }
}
