package com.grytaJan.ExpenseTracker.controllers.Comment;

import com.grytaJan.ExpenseTracker.controllers.Comment.dto.CommentDto;
import com.grytaJan.ExpenseTracker.controllers.Comment.dto.CreateCommentDto;
import com.grytaJan.ExpenseTracker.errors.ResourceNotFoundException;
import com.grytaJan.ExpenseTracker.models.Comment;
import com.grytaJan.ExpenseTracker.models.RoleConstants;
import com.grytaJan.ExpenseTracker.services.CommentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Comments;
import org.hibernate.type.ListType;
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
    public ResponseEntity<List<CommentDto>> getAllComments() {
        List<Comment> comments = commentService.getAll();

        return new ResponseEntity<>(commentListToDtos(comments), HttpStatus.OK);
    }

    @GetMapping("/myComments")
    public ResponseEntity<List<CommentDto>> getMyComments() throws ResourceNotFoundException {
        List<Comment> comments = commentService.getUserComments();
        return new ResponseEntity<>(commentListToDtos(comments), HttpStatus.OK);
    }

    @GetMapping("/{task_id}")
    public ResponseEntity<List<CommentDto>> getAllCommentsForTask(@PathVariable @Positive long task_id) throws ResourceNotFoundException {
        List<Comment> comments = commentService.getAllForTask(task_id);
        return new ResponseEntity<>(commentListToDtos(comments), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<CommentDto> createComment(@Valid @RequestBody CreateCommentDto comment) throws ResourceNotFoundException {
        Comment c = commentService.createComment(comment);
        return new ResponseEntity<>(new CommentDto(c), HttpStatus.CREATED);
    }

    private List<CommentDto> commentListToDtos(List<Comment> comments) {
        List<CommentDto> dtos = new ArrayList<>();
        for(Comment c : comments) {
            dtos.add(new CommentDto(c));
        }
        return dtos;
    }



}
