package com.example.my.todo.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.example.my.todo.entity.TodoEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class TodoDTO {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReqBasic {
        private String content;

        public TodoEntity toEntity() {
            return TodoEntity.builder()
                    .content(content)
                    .doneYn('N')
                    .deleteYn('N')
                    .createDate(LocalDateTime.now())
                    .build();
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResBasic {
        private List<Todo> todoList;

        // 해당 static class 내부에서만 사용하는 클래스
        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        static class Todo {
            Integer idx;
            String content;
            Character doneYn;
        }

        // 엔티티에서 DTO로 변환
        public static ResBasic fromEntityList(List<TodoEntity> todoEntityList) {
            List<Todo> todoList = todoEntityList.stream().map(todoEntity -> {
                return Todo.builder()
                        .idx(todoEntity.getIdx())
                        .content(todoEntity.getContent())
                        .doneYn(todoEntity.getDoneYn())
                        .build();
            }).collect(Collectors.toList());

            return new ResBasic(todoList);
            // return ResBasic.builder().todoList(todoList).build();
        }
    }
}