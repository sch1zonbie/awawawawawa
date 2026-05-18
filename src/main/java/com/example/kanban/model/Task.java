package com.example.kanban.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table
@Data
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    //чтобы сохранять статусы NEW, IN_PROGRESS, DONE в виде строк, а не в виде чисел
    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        NEW, IN_PROGRESS, DONE
    }
}