package com.example.kanban.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table
@Data
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    private LocalDate deadline;

    @ManyToOne
    @JoinColumn(name = "worker_id")
    private Worker assignee;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    public enum Priority {
        LOW, MEDIUM, HIGH
    }

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        NEW, IN_PROGRESS, DONE
    }

    @ManyToOne
    @JoinColumn(name = "board_id")
    private PersonalBoard board;
}