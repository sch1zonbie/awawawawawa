package com.example.kanban.repository;

import com.example.kanban.model.PersonalBoard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<PersonalBoard, Long> {
    Optional<PersonalBoard>findByWorkerId(Long workerId);
}
