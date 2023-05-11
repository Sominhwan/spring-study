package com.example.my.module.todo.repository;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.example.my.module.todo.entity.TodoEntity;

@Repository
@Mapper
public interface TodoRepository {

    TodoEntity findByIdx(Integer idx);

    List<TodoEntity> findByDeleteYn(Character deleteYn);

    List<TodoEntity> findByUserIdxAndDeleteYn(@Param("userIdx") Integer userIdx, @Param("deleteYn") Character deleteYn);

    Integer insert(TodoEntity todoEntity);

    Integer update(TodoEntity todoEntity);

}
