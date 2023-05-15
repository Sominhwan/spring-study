package com.example.my.module.todo.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.my.common.dto.ResDTO;
import com.example.my.common.exception.BadRequestException;
import com.example.my.config.security.CustomUserDetails;
import com.example.my.module.todo.dto.TodoDTO;
import com.example.my.module.todo.entity.TodoEntity;
import com.example.my.module.todo.repository.TodoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoServiceApiV3 {

        private final TodoRepository todoRepository;

        public HttpEntity<?> findByDeleteYn(CustomUserDetails customUserDetails, Character deleteYn) {

                // List<TodoEntity> todoEntityList = todoRepository.findByDeleteYn(deleteYn);

                // 로그인 한 유저가 쓴 할 일만 가져옴
                List<TodoEntity> todoEntityList = todoRepository
                                .findByUserIdxAndDeleteYn(customUserDetails.getUserEntity().getIdx(), deleteYn);

                return new ResponseEntity<>(
                                ResDTO.builder()
                                                .code(0)
                                                .message("할 일 조회에 성공하였습니다.")
                                                // .data(TodoDTO.ResBasic.fromEntityList(todoEntityList))
                                                .data(TodoDTO.ResMy.fromUserEntitiyAndTodoEntityList(
                                                                customUserDetails.getUserEntity(), todoEntityList))
                                                .build(),
                                HttpStatus.OK);
        }

        @Transactional
        public HttpEntity<?> insert(CustomUserDetails customUserDetails, TodoDTO.ReqBasic reqDto) {

                // 본인이 적은 글임을 DB에 넣어준다
                todoRepository.insert(reqDto.toEntity(customUserDetails.getUserEntity().getIdx()));

                return new ResponseEntity<>(
                                ResDTO.builder()
                                                .code(0)
                                                .message("할 일 추가에 성공하였습니다.")
                                                .build(),
                                HttpStatus.OK);
        }

        @Transactional
        public HttpEntity<?> update(CustomUserDetails customUserDetails, Integer idx) {
                TodoEntity todoEntity = todoRepository.findByIdx(idx);

                // 본인 글이 아닐 시 에러 발생
                if (todoEntity.getUserIdx() != customUserDetails.getUserEntity().getIdx()) {
                        throw new BadRequestException("잘못된 요청 입니다.");
                }

                if (todoEntity.getDoneYn().equals('N')) {
                        todoEntity.setDoneYn('Y');
                } else {
                        todoEntity.setDoneYn('N');
                }
                todoEntity.setUpdateDate(LocalDateTime.now());

                todoRepository.update(todoEntity);

                return new ResponseEntity<>(
                                ResDTO.builder()
                                                .code(0)
                                                .message(todoEntity.getIdx() + "번 할 일(" + todoEntity.getContent()
                                                                + ") 수정에 성공하였습니다.")
                                                .build(),
                                HttpStatus.OK);
        }

        @Transactional
        public HttpEntity<?> delete(CustomUserDetails customUserDetails, Integer idx) {

                TodoEntity todoEntity = todoRepository.findByIdx(idx);

                // 본인 글이 아닐 시 에러 발생
                if (todoEntity.getUserIdx() != customUserDetails.getUserEntity().getIdx()) {
                        throw new BadRequestException("잘못된 요청 입니다.");
                }

                todoEntity.setDeleteYn('Y');
                todoEntity.setDeleteDate(LocalDateTime.now());

                todoRepository.update(todoEntity);

                // 리턴값 수정
                // 1번 할 일(잠자기) 삭제에 성공하였습니다.
                return new ResponseEntity<>(
                                ResDTO.builder()
                                                .code(0)
                                                .message(todoEntity.getIdx() + "번 할 일(" + todoEntity.getContent()
                                                                + ") 삭제에 성공하였습니다.")
                                                .build(),
                                HttpStatus.OK);
        }

}
