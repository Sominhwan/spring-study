package com.example.my.todo.repository;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.example.my.todo.entity.TodoEntity;

@Repository
@Mapper
public interface TodoRepository {

    TodoEntity findByIdx(Integer idx);

    List<TodoEntity> findByDeleteYn(Character deleteYn);

    Integer insert(TodoEntity todoEntity);

    Integer update(TodoEntity todoEntity);



}
