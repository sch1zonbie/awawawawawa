package com.example.kanban.service;

import com.example.kanban.dto.BoardDto;
import com.example.kanban.dto.TaskDto;
import com.example.kanban.model.PersonalBoard;
import com.example.kanban.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository repo;

    public BoardDto getBoardByWorkerId(Long workerId) {
        PersonalBoard board = repo.findByWorkerId(workerId)
                .orElseThrow(() -> new RuntimeException("Доска не найдена"));
        return mapToDto(board);
    }

    private BoardDto mapToDto(PersonalBoard board) {
        BoardDto dto = new BoardDto();
        dto.setId(board.getBoard_id());
        dto.setWorkerId(board.getWorker().getId());

        List<TaskDto> taskDtos = board.getTasks().stream()
                .map(task -> {
                    TaskDto taskDto = new TaskDto();
                    taskDto.setId(task.getId());
                    taskDto.setTitle(task.getTitle());
                    taskDto.setDescription(task.getDescription());
                    taskDto.setDeadline(task.getDeadline());
                    taskDto.setWorkerId(task.getAssignee().getId());
                    taskDto.setPriority(task.getPriority());
                    taskDto.setStatus(task.getStatus());
                    return taskDto;
                } )
                .collect(Collectors.toList());

        dto.setTasks(taskDtos);
        return dto;
    }
}
