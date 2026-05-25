package com.example.kanban.service;

import com.example.kanban.model.Worker;
import com.example.kanban.repository.WorkerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkerService {

    private final WorkerRepository repo;

    public List<Worker> getAll() {
        return repo.findAll();
    }

    public Worker create(Worker worker) {
        return repo.save(worker);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}