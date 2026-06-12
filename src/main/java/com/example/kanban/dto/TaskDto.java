package com.example.kanban.dto;

import com.example.kanban.model.Task.Priority;
import com.example.kanban.model.Task.Status;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskDto {
    private Long id;
    private String title;
    private String description;
    private LocalDate deadline;
    private String assignee;
    private Priority priority;
    private Status status;
}
