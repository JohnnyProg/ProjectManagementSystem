package com.grytaJan.ExpenseTracker.repositories;

import com.grytaJan.ExpenseTracker.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
