package com.grytaJan.ExpenseTracker.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "project_seq_gen")
    @SequenceGenerator(name = "project_seq_gen", sequenceName = "project_seq", allocationSize = 1)
    private long id;

    private String content;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name="task_id", nullable = false)
    private Task task;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
