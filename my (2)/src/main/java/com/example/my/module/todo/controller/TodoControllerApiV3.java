package com.example.my.module.todo.controller;

import org.springframework.http.HttpEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.my.config.security.CustomUserDetails;
import com.example.my.module.todo.dto.TodoDTO;
import com.example.my.module.todo.service.TodoServiceApiV3;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v3/todo")
public class TodoControllerApiV3 {

    private final TodoServiceApiV3 todoServiceApiV3;

    @GetMapping
    public HttpEntity<?> select(@AuthenticationPrincipal CustomUserDetails customUserDetails) {

        log.info("리스트를 가져옵니다.");
        return todoServiceApiV3.findByDeleteYn(customUserDetails, 'N');
    }

    @PostMapping
    public HttpEntity<?> insert(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @Validated @RequestBody TodoDTO.ReqBasic reqDto) {
        log.info("할 일 ()" + reqDto.getContent() + ") 추가를 요청합니다.");
        return todoServiceApiV3.insert(customUserDetails, reqDto);
    }

    @PutMapping("/{idx}")
    public HttpEntity<?> update(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable Integer idx) {
        log.info(idx + "번 할 일 수정을 요청합니다.");
        return todoServiceApiV3.update(customUserDetails, idx);
    }

    @DeleteMapping("/{idx}")
    public HttpEntity<?> delete(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable Integer idx) {
        log.warn(idx + "번 할 일 삭제를 요청합니다.");
        return todoServiceApiV3.delete(customUserDetails, idx);
    }
}
