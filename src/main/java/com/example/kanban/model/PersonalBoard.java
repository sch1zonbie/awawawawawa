package com.example.kanban.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table
@Data

public class PersonalBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


}
