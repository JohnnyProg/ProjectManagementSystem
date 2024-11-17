package com.grytaJan.ExpenseTracker.controllers.project;

import com.grytaJan.ExpenseTracker.controllers.project.dto.CreateProjectDto;
import com.grytaJan.ExpenseTracker.controllers.project.dto.ProjectDto;
import com.grytaJan.ExpenseTracker.errors.ResourceNotFoundException;
import com.grytaJan.ExpenseTracker.models.Project;
import com.grytaJan.ExpenseTracker.models.Role;
import com.grytaJan.ExpenseTracker.models.RoleConstants;
import com.grytaJan.ExpenseTracker.services.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping
    @Secured({RoleConstants.ROLE_ADMIN, RoleConstants.ROLE_MANAGER})
    public ResponseEntity<List<ProjectDto>> getAllProjects() {
        List<Project> projects =  projectService.getAllProjects();

        List<ProjectDto> dtos = new ArrayList<>();
        projects.forEach(project -> {
            dtos.add(new ProjectDto(project));
        });
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Secured({RoleConstants.ROLE_ADMIN, RoleConstants.ROLE_MANAGER})
    public ResponseEntity<ProjectDto> getProjectById(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<Project> project = projectService.getProjectById(id);
        if(project.isPresent()) {
            return new ResponseEntity<>(new ProjectDto(project.get()), HttpStatus.OK);
        }
        throw new ResourceNotFoundException("project", id.toString());
    }

    @PostMapping
    @Secured({RoleConstants.ROLE_ADMIN, RoleConstants.ROLE_MANAGER})
    public ResponseEntity<ProjectDto> createProject(@RequestBody CreateProjectDto project) {
        Project project1 =  projectService.createProject(project);
        return new ResponseEntity<>(new ProjectDto(project1), HttpStatus.CREATED);
    }


    @DeleteMapping("/{id}")
    @Secured({RoleConstants.ROLE_ADMIN, RoleConstants.ROLE_MANAGER})
    public void deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
    }
}
