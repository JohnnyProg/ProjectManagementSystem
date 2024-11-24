package com.grytaJan.ExpenseTracker.services;

import com.grytaJan.ExpenseTracker.controllers.Comment.dto.CreateCommentDto;
import com.grytaJan.ExpenseTracker.errors.ResourceNotFoundException;
import com.grytaJan.ExpenseTracker.models.Comment;
import com.grytaJan.ExpenseTracker.models.Task;
import com.grytaJan.ExpenseTracker.models.User;
import com.grytaJan.ExpenseTracker.repositories.CommentRepository;
import com.grytaJan.ExpenseTracker.repositories.TaskRepository;
import com.grytaJan.ExpenseTracker.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    public List<Comment> getAll() {
        return commentRepository.findAll();
    }

    public Page<Comment> getAll(Pageable pageable) {
        return commentRepository.findAll(pageable);
    }

    public Page<Comment> getAllForTask(long taskId, Pageable pageable) throws ResourceNotFoundException {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task", Long.toString(taskId)));
        Page<Comment> comments = commentRepository.findByTaskId(task.getId(), pageable);
        return comments;
    }

    public Comment createComment(CreateCommentDto comment) throws ResourceNotFoundException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Task task = taskRepository.findById(comment.getTaskId())
                .orElseThrow(() -> new ResourceNotFoundException("Task", Long.toString(comment.getTaskId())));

        Comment newComment = Comment.builder()
                .content(comment.getContent())
                .task(task)
                .user(user)
                .build();
        return commentRepository.save(newComment);

    }

    public List<Comment> getUserComments() throws ResourceNotFoundException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User realUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", Long.toString(user.getId())));
        return realUser.getComments();
    }

    public Page<Comment> getUserComments(Pageable pageable) throws ResourceNotFoundException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return commentRepository.findByUser(user, pageable);
    }
}
