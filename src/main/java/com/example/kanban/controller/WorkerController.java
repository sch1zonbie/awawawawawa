package com.example.kanban.controller;

import com.example.kanban.model.Worker;
import com.example.kanban.service.WorkerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workers")
@RequiredArgsConstructor
public class WorkerController {

    private final WorkerService service;

    @GetMapping
    public List<Worker> getAll() {
        return service.getAll();
    }

    @PostMapping
    public Worker create(@RequestBody Worker worker) {
        return service.create(worker);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}