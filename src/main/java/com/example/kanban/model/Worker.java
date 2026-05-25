package com.example.kanban.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "workers")
@Data

public class Worker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
}
