package com.example.my.todo.dto;

import lombok.Data;

public class TodoDTO {

    @Data
    public static class ReqHello {
        private Integer idx;
        private String search;
    }
}
