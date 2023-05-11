package com.example.my.module.todo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.my.module.todo.entity.TodoEntity;
import com.example.my.module.todo.service.TodoService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @GetMapping("/todoList")
    public String todoList(Model model) {

        List<TodoEntity> todoList = todoService.findByDeleteYn('N');
        long todoCount = todoList.stream().filter((todoEntity) -> todoEntity.getDoneYn().equals('N')).count();
        
        model.addAttribute("todoList", todoList);
        model.addAttribute("todoCount", todoCount);

        return "todoList";
    }

}
