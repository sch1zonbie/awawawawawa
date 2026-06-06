package com.example.kanban.repository;

import com.example.kanban.model.PersonalBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<PersonalBoard, Long> {
}
