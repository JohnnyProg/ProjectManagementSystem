package com.grytaJan.ExpenseTracker.repositories;

import com.grytaJan.ExpenseTracker.models.Comment;
import com.grytaJan.ExpenseTracker.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findByUser(User user, Pageable pageable);
    Page<Comment> findByTaskId(Long taskId, Pageable pageable);
}
