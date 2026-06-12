package com.example.kanban.dto;

import lombok.Data;

import java.util.List;

@Data
public class BoardDto {
    private Long id;
    private Long workerId;
    private List<TaskDto> tasks;

}
