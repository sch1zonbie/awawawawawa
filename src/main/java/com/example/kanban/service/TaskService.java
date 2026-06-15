package com.example.kanban.service;

import com.example.kanban.model.Task;
import com.example.kanban.repository.BoardRepository;
import com.example.kanban.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository repo;
    private final BoardRepository boardRepo;

    public List<Task> getAll() {
        return repo.findAll();
    }

    public Task create(Task task) {
        if (task.getAssignee() != null) {
            boardRepo.findByWorkerId(task.getAssignee().getId())
                    .ifPresent(task::setBoard);
        }
        return repo.save(task);
    }

    public Task updateTitle(Long id, String title) {
        Task task = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Таска не найдена: " + id));
        task.setTitle(title);
        return repo.save(task);
    }

    public Task updateStatus(Long id, Task.Status status) {
        Task task = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Таска не найдена: " + id));
        task.setStatus(status);
        return repo.save(task);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    public Task update(Long id, Task patch) {
        Task task = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Таска не найдена: " + id));

        task.setTitle(patch.getTitle());
        task.setStatus(patch.getStatus());
        task.setDescription(patch.getDescription());
        task.setDeadline(patch.getDeadline());
        task.setAssignee(patch.getAssignee());
        task.setPriority(patch.getPriority());

        if (patch.getAssignee() != null) {
            boardRepo.findByWorkerId(patch.getAssignee().getId())
                    .ifPresent(task::setBoard);
        }

        return repo.save(task);
    }
}