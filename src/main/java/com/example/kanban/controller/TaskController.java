package com.example.kanban.controller;

import com.example.kanban.model.Task;
import com.example.kanban.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tasks")

//автоматом создает конструктор для файнал полей
@RequiredArgsConstructor
public class TaskController {

    private final TaskService service;

    @GetMapping
    public List<Task> getAll() {
        return service.getAll();
    }

    @PostMapping
    public Task create(@RequestBody Task task) {
        return service.create(task);
    }

    @PatchMapping("/{id}/title")
    public Task updateTitle(@PathVariable Long id,
                            @RequestBody Map<String, String> body) {
        return service.updateTitle(id, body.get("title"));
    }

    @PatchMapping("/{id}/status")
    public Task updateStatus(@PathVariable Long id,
                             @RequestBody Map<String, String> body) {
        Task.Status status = Task.Status.valueOf(body.get("status"));
        return service.updateStatus(id, status);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}