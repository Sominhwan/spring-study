package com.example.my.config.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.my.module.user.entity.UserEntity;
import com.example.my.module.user.entity.UserRoleEntity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

// implements UserDetails 는 시큐리티에서 제공하는 인터페이스
// 스프링 시큐리티에서 사용하는 유저 정보 저장소
@Getter
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    // UserDetails 에 UserEntity 를 넣어 놓으면
    // 컨트롤러나 서비스에서 편하게 사용 가능
    private final UserEntity userEntity;
    private final List<UserRoleEntity> userRoleEntityList;

    // 유저가 가진 모든 권한 가져오기
    // 유저는 여러가지 권한을 가질 수 있다
    // 네이버 카페 운영
    // 카페 개설자 ADMIN
    // 카페 이용자 USER
    // 댓글을 10번 적어서 새싹회원 -> 열심회원
    // 이용자 중에 스태프로 선정
    // 해당 이용자는 USER, 열심회원, STAFF
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 1. userRoleEntityList 에서 role(String) 만 가져오기
        // 2. role으로 GrantedAuthority 객체 만들기
        // 3. List에 집어 넣기

        Collection<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        for (UserRoleEntity userRoleEntity : userRoleEntityList) {
            grantedAuthorityList.add(new GrantedAuthority() {
                // 익명 클래스
                // 인터페이스가 구현해 달라고 한 것들만 구현하면 익명 클래스를 만들 수 있다.
                @Override
                public String getAuthority() {
                    // 스프링 시큐리티에서는 권한 앞에 ROLE_ 를 붙인다.
                    return "ROLE_" + userRoleEntity.getRole();
                }
            });
        }
        // 결과값 리턴
        return grantedAuthorityList;
    }

    // 비밀번호 pw 가져오기
    @Override
    public String getPassword() {
        return userEntity.getPw();
    }

    // 아이디 ud 가져오기
    @Override
    public String getUsername() {
        return userEntity.getId();
    }

    // 계정이 만료되지 않았는지 체크
    // ex 한달 유로 구독한 계정 -> 한달 뒤에 만료
    @Override
    public boolean isAccountNonExpired() {
        // 만료될 서비스가 아니라서 무조건 true
        return true;
    }

    // 신고를 당하거나, 휴먼 계정 같이 잠겼는지 확인
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 인증이 만료되었는지 확인
    @Override
    public boolean isCredentialsNonExpired() {
        // 세션 방식이라서 true 고정
        return true;
    }

    // 일시 정지, 신고 등
    @Override
    public boolean isEnabled() {
        // 개인이 사용하는 todoList 라서 필요 없음
        return true;
    }

}
