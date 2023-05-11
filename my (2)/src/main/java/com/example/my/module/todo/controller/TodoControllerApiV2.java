package com.example.my.module.todo.controller;

import org.springframework.http.HttpEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.my.module.todo.dto.TodoDTO;
import com.example.my.module.todo.service.TodoServiceApiV2;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/todo")
public class TodoControllerApiV2 {
    
    private final TodoServiceApiV2 todoServiceApiV2;

    @GetMapping
    public HttpEntity<?> select() {
        log.info("리스트를 가져옵니다.");
        return todoServiceApiV2.findByDeleteYn('N');
    }

    @PostMapping
    public HttpEntity<?> insert(@Validated @RequestBody TodoDTO.ReqBasic reqDto) {
        log.info("할 일 ()" + reqDto.getContent() + ") 추가를 요청합니다.");
        return todoServiceApiV2.insert(reqDto);
    }

    @PutMapping("/{idx}")
    public HttpEntity<?> update(@PathVariable Integer idx) {
        log.info(idx + "번 할 일 수정을 요청합니다.");
        return todoServiceApiV2.update(idx);
    }

    @DeleteMapping("/{idx}")
    public HttpEntity<?> delete(@PathVariable Integer idx) {
        log.warn(idx + "번 할 일 삭제를 요청합니다.");
        return todoServiceApiV2.delete(idx);
    }
}
