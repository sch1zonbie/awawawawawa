package com.example.kanban.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JoinColumn(name = "workers_id") //разобраться наконец-то с этой колонной и workers и worker_id и workerID расплодились тут
    private Worker worker;

    @JsonIgnore
    @OneToMany(mappedBy = "board")
    private List<Task> tasks;
}
