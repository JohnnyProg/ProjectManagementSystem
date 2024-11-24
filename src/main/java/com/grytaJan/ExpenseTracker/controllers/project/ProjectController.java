package com.grytaJan.ExpenseTracker.controllers.project;

import com.grytaJan.ExpenseTracker.controllers.Comment.dto.CommentDto;
import com.grytaJan.ExpenseTracker.controllers.project.dto.CreateProjectDto;
import com.grytaJan.ExpenseTracker.controllers.project.dto.ProjectDto;
import com.grytaJan.ExpenseTracker.errors.ResourceNotFoundException;
import com.grytaJan.ExpenseTracker.models.Comment;
import com.grytaJan.ExpenseTracker.models.Project;
import com.grytaJan.ExpenseTracker.models.Role;
import com.grytaJan.ExpenseTracker.models.RoleConstants;
import com.grytaJan.ExpenseTracker.services.ProjectService;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
@Validated
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping
    @Secured({RoleConstants.ROLE_ADMIN, RoleConstants.ROLE_MANAGER})
    public ResponseEntity<PageResponse<ProjectDto>> getAllProjects(
            @Valid @ModelAttribute PaginationInfo paginationInfo

    ) {
        Pageable pageable = PaginationUtils.createPageable(paginationInfo);


        Page<Project> projects =  projectService.getAllProjects(pageable);


        return new ResponseEntity<>(this.projectPageToPageResponse(projects), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Secured({RoleConstants.ROLE_ADMIN, RoleConstants.ROLE_MANAGER})
    public ResponseEntity<ProjectDto> getProjectById(@PathVariable @Positive Long id) throws ResourceNotFoundException {
        Project project = projectService.getProjectById(id);

        return new ResponseEntity<>(new ProjectDto(project), HttpStatus.OK);

    }

    @PostMapping
    @Secured({RoleConstants.ROLE_ADMIN, RoleConstants.ROLE_MANAGER})
    public ResponseEntity<ProjectDto> createProject(@RequestBody @Valid CreateProjectDto project) {
        Project project1 =  projectService.createProject(project);
        return new ResponseEntity<>(new ProjectDto(project1), HttpStatus.CREATED);
    }


    @DeleteMapping("/{id}")
    @Secured({RoleConstants.ROLE_ADMIN, RoleConstants.ROLE_MANAGER})
    public void deleteProject(@PathVariable @Positive Long id) {
        projectService.deleteProject(id);
    }

    private Page<ProjectDto> projectPageToDtos(Page<Project> comments) {
        return comments.map(ProjectDto::new);
    }


    private PageResponse<ProjectDto> projectPageToPageResponse(Page<Project> projects) {
        return new PageResponse<>(this.projectPageToDtos(projects));
    }
}
