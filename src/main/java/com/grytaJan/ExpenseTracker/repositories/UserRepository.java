package com.grytaJan.ExpenseTracker.repositories;

import com.grytaJan.ExpenseTracker.models.Task;
import com.grytaJan.ExpenseTracker.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsernameOrEmail(String username, String email);
    default Optional<User> findByUsernameOrEmail(String identifier) {
        return this.findByUsernameOrEmail(identifier, identifier);
    }

    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);

    List<User> findByIdIn(List<Long> ids);
}
