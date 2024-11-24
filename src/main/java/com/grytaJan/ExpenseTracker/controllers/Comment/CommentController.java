package com.grytaJan.ExpenseTracker.controllers.Comment;

import com.grytaJan.ExpenseTracker.controllers.Comment.dto.CommentDto;
import com.grytaJan.ExpenseTracker.controllers.Comment.dto.CreateCommentDto;
import com.grytaJan.ExpenseTracker.errors.ResourceNotFoundException;
import com.grytaJan.ExpenseTracker.models.Comment;
import com.grytaJan.ExpenseTracker.models.RoleConstants;
import com.grytaJan.ExpenseTracker.services.CommentService;
import com.grytaJan.ExpenseTracker.utils.pagination.PageResponse;
import com.grytaJan.ExpenseTracker.utils.pagination.PaginationInfo;
import com.grytaJan.ExpenseTracker.utils.pagination.PaginationUtils;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Comments;
import org.hibernate.type.ListType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
@Validated
public class CommentController {
    private final CommentService commentService;

    @GetMapping
    @Secured({RoleConstants.ROLE_ADMIN, RoleConstants.ROLE_MANAGER})
    public ResponseEntity<PageResponse<CommentDto>> getAllComments(
            @Valid @ModelAttribute PaginationInfo paginationInfo
    ) {
        Pageable pageable = PaginationUtils.createPageable(paginationInfo);

        Page<Comment> comments = commentService.getAll(pageable);

        return ResponseEntity.ok(this.commentPageToPageResponse(comments));
    }

    @GetMapping("/myComments")
    public ResponseEntity<PageResponse<CommentDto>> getMyComments(
            @Valid @ModelAttribute PaginationInfo paginationInfo
    ) throws ResourceNotFoundException {

        Pageable pageable = PaginationUtils.createPageable(paginationInfo);

        Page<Comment> comments = commentService.getUserComments(pageable);

        return ResponseEntity.ok(this.commentPageToPageResponse(comments));
    }

    @GetMapping("/{task_id}")
    public ResponseEntity<PageResponse<CommentDto>> getAllCommentsForTask(
            @PathVariable @Positive long task_id,
            @Valid @ModelAttribute PaginationInfo paginationInfo
    ) throws ResourceNotFoundException {

        Pageable pageable = PaginationUtils.createPageable(paginationInfo);

        Page<Comment> comments = commentService.getAllForTask(task_id, pageable);
        return ResponseEntity.ok(this.commentPageToPageResponse(comments));
    }

    @PostMapping("/")
    public ResponseEntity<CommentDto> createComment(@Valid @RequestBody CreateCommentDto comment) throws ResourceNotFoundException {
        Comment c = commentService.createComment(comment);
        return new ResponseEntity<>(new CommentDto(c), HttpStatus.CREATED);
    }

    private Page<CommentDto> commentPageToDtos(Page<Comment> comments) {
        return comments.map(CommentDto::new);
    }


    private PageResponse<CommentDto> commentPageToPageResponse(Page<Comment> comments) {
        return new PageResponse<>(this.commentPageToDtos(comments));
    }


}
