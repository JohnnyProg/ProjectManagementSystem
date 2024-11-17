package com.grytaJan.ExpenseTracker.UnitTests.Services;
import com.grytaJan.ExpenseTracker.controllers.project.dto.CreateProjectDto;
import com.grytaJan.ExpenseTracker.errors.ResourceNotFoundException;
import com.grytaJan.ExpenseTracker.models.Comment;
import com.grytaJan.ExpenseTracker.models.Project;
import com.grytaJan.ExpenseTracker.models.Task;
import com.grytaJan.ExpenseTracker.models.User;
import com.grytaJan.ExpenseTracker.repositories.CommentRepository;
import com.grytaJan.ExpenseTracker.repositories.ProjectRepository;
import com.grytaJan.ExpenseTracker.repositories.TaskRepository;
import com.grytaJan.ExpenseTracker.repositories.UserRepository;
import com.grytaJan.ExpenseTracker.services.CommentService;

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
public class TestProjectService {
    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProjectService projectService;


    @Test
    void testGetAllProjects() {
        List<Project> mockProjects = List.of(new Project(), new Project());
        when(projectRepository.findAll()).thenReturn(mockProjects);

        List<Project> allProjects = projectService.getAllProjects();

        assertEquals(mockProjects, allProjects);
        verify(projectRepository).findAll();
    }

    @Test
    void testGetProjectById_Success() throws ResourceNotFoundException {
        long projectId = 1L;
        Project mockProject = new Project();
        mockProject.setId(projectId);
        Optional<Project> optionalProject = Optional.of(mockProject);

        when(projectRepository.findById(projectId)).thenReturn(optionalProject);

        Project project = projectService.getProjectById(projectId);

        assertEquals(mockProject, project);
        verify(projectRepository).findById(projectId);
    }

    @Test
    void testGetProjectById_NotFound() throws ResourceNotFoundException {
        long projectId = 1L;

        when(projectRepository.findById(projectId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> projectService.getProjectById(projectId));
        verify(projectRepository).findById(projectId);
    }

    @Test
    void testCreateProject() {
        CreateProjectDto projectDto = new CreateProjectDto("Test Project", "This is a test project");
        Project newProject = new Project();
        newProject.setName(projectDto.getName());
        newProject.setDescription(projectDto.getDescription());

        when(projectRepository.save(newProject)).thenReturn(newProject);

        Project createdProject = projectService.createProject(projectDto);

        assertEquals(newProject, createdProject);
        verify(projectRepository).save(newProject);
    }

    @Test
    void testUpdateProject_Success() {
        long projectId = 1L;
        Project existingProject = new Project();
        existingProject.setId(projectId);
        existingProject.setName("Old Name");
        existingProject.setDescription("Old Description");

        Project updatedProject = new Project();
        updatedProject.setId(projectId);
        updatedProject.setName("New Name");
        updatedProject.setDescription("New Description");

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(existingProject));
        when(projectRepository.save(existingProject)).thenReturn(updatedProject);

        Project returnedProject = projectService.updateProject(projectId, updatedProject);

        assertEquals(updatedProject, returnedProject);
        verify(projectRepository).findById(projectId);
        verify(projectRepository).save(existingProject);
    }

    @Test
    void testUpdateProject_NotFound() {
        long projectId = 1L;
        Project projectDetails = new Project();

        when(projectRepository.findById(projectId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> projectService.updateProject(projectId, projectDetails));
        verify(projectRepository).findById(projectId);
    }

}
