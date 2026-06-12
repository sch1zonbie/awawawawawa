package com.example.kanban.service;

import com.example.kanban.model.PersonalBoard;
import com.example.kanban.model.Worker;
import com.example.kanban.repository.BoardRepository;
import com.example.kanban.repository.WorkerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkerService {

    private final WorkerRepository repo;
    private final BoardRepository boardRepository;

    public List<Worker> getAll() {
        return repo.findAll();
    }

    public Worker create(Worker worker) {
        Worker saved = repo.save(worker);

        PersonalBoard board = new PersonalBoard();
        board.setWorker(saved);
        boardRepository.save(board);

        return saved;
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}