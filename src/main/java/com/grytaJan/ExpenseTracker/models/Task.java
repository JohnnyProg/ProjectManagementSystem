package com.grytaJan.ExpenseTracker.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "task_seq_gen")
    @SequenceGenerator(name = "task_seq_gen", sequenceName = "task_seq", allocationSize = 1)
    private long id;

    private String title;
    private String description;
    @Enumerated(EnumType.STRING)
    private Priority priority;
    @Enumerated(EnumType.STRING)
    private Status status;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
//    @JsonBackReference // Prevent serialization of the parent (Project)
    private Project project;

    @ManyToMany()
    @JoinTable(name = "task_user", joinColumns = @JoinColumn(name="task_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<User> users = new HashSet<>();

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

//    public void addUser(User user) {
//        if (!this.users.contains(user)) { // Prevent duplication
//            this.users.add(user);
//            user.addTask(this); // Update the other side
//        }
//    }
//
//    public void removeUser(User user) {
//        this.users.remove(user);
//        user.getTasks().remove(this); // Update the User's task list as well
//    }
}



