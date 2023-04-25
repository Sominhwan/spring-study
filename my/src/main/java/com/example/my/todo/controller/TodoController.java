package com.example.my.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.my.todo.entity.TodoEntity;
import com.example.my.todo.repository.TodoRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class TodoController {

    private final TodoRepository todoRepository;

    @GetMapping("/")
    @ResponseBody
    public TodoEntity hello() {
        return todoRepository.findByIdx(1);
    }

    @GetMapping("/todoList")
    public String todoList() {
        return "todoList";
    }

}
