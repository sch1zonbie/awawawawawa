package com.example.kanban.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table
@Data

public class PersonalBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long board_id;

    @OneToOne
    @JoinColumn(name = "workers_id")
    private Worker worker;

    @OneToMany(mappedBy = "board")
    private List<Task> tasks;
}
