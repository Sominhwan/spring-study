package com.example.my.module.user.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.example.my.module.user.entity.UserRoleEntity;

@Repository
@Mapper
public interface UserRoleRepository {

    List<UserRoleEntity> findByUserIdx(Integer userIdx);

    List<String> findRoleByUserIdx(Integer userIdx);

    UserRoleEntity findByUserIdxAndRole(@Param("userIdx") Integer userIdx, @Param("role") String role);

    Integer insert(UserRoleEntity userRoleEntity);
}
