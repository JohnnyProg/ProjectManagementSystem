package com.grytaJan.ExpenseTracker.repositories;

import com.grytaJan.ExpenseTracker.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
