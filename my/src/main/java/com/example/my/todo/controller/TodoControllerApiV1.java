package com.example.my.todo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.my.todo.service.TodoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class TodoControllerApiV1 {
    private final TodoService todoService;

    @GetMapping("/api/v1/insert")
    public void insert(@RequestParam String content) {
        todoService.insert(content);
    }

    @GetMapping("/api/v1/update")
    public void update(@RequestParam Integer idx) {
        todoService.update(idx);
    }

    @GetMapping("/api/v1/delete")
    public void delete(@RequestParam Integer idx) {
        todoService.delete(idx);
    }
}
