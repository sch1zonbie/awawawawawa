package com.example.kanban.controller;

import com.example.kanban.dto.BoardDto;
import com.example.kanban.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService service;

    @GetMapping("/worker/{workerId}")
    public ResponseEntity<BoardDto> getBoardByWorker(@PathVariable Long workerId) {
        BoardDto board = service.getBoardByWorkerId(workerId);
        return ResponseEntity.ok(board);
    }
}
