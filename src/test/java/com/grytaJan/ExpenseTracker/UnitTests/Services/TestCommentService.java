package com.grytaJan.ExpenseTracker.UnitTests.Services;

import com.grytaJan.ExpenseTracker.errors.ResourceNotFoundException;
import com.grytaJan.ExpenseTracker.models.Comment;
import com.grytaJan.ExpenseTracker.models.Task;
import com.grytaJan.ExpenseTracker.models.User;
import com.grytaJan.ExpenseTracker.repositories.CommentRepository;
import com.grytaJan.ExpenseTracker.repositories.TaskRepository;
import com.grytaJan.ExpenseTracker.repositories.UserRepository;
import com.grytaJan.ExpenseTracker.services.CommentService;

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
public class TestCommentService {
    @Mock
    private CommentRepository commentRepository;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CommentService commentService;

    @BeforeAll
    public static void setUp() {
        SecurityContextHolder.clearContext(); // Clear any existing context
    }

    @Test
    void testGetAll() {
        List<Comment> mockComments = List.of(new Comment(), new Comment());
        when(commentRepository.findAll()).thenReturn(mockComments);

        List<Comment> allComments = commentService.getAll();

        assertEquals(mockComments, allComments);
        verify(commentRepository).findAll();
        System.out.println("done");
    }

    @Test
    void testGetUserComments_Success() throws ResourceNotFoundException {
        User mockUser = new User();
        List<Comment> mockComments = List.of(new Comment(), new Comment());
        mockUser.setComments(mockComments);

        Authentication mockAuthentication = Mockito.mock(Authentication.class);
        when(mockAuthentication.getPrincipal()).thenReturn(mockUser);
        SecurityContextHolder.getContext().setAuthentication(mockAuthentication);

        when(userRepository.findById(mockUser.getId())).thenReturn(Optional.of(mockUser));

        List<Comment> userComments = commentService.getUserComments();

        assertEquals(mockComments, userComments);
        verify(userRepository).findById(mockUser.getId());
    }


}
