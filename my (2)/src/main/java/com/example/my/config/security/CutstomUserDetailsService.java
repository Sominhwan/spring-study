package com.example.my.config.security;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.my.module.user.entity.UserEntity;
import com.example.my.module.user.entity.UserRoleEntity;
import com.example.my.module.user.repository.UserRepository;
import com.example.my.module.user.repository.UserRoleRepository;

import lombok.RequiredArgsConstructor;

// UserDetailsService 가 기존에 있음
// 시큐리티 디펜던시 추가했을 때 로그인 했었음 user 라는 아이디로
// Service라고 붙이면 UserDetailsService 중에서 하나만 뜬다
// Bean 또는 Component 는 interface 기준으로 하나만 떠야 된다.
@Service
@Primary // Primary를 붙이면 중복된 Component 중에서 1순위로 roc 컨테이너에 등록된다.
@RequiredArgsConstructor
public class CutstomUserDetailsService implements UserDetailsService {

    // DI
    // 1. @Autowired
    // 2. setter
    // 3. 생성자 -> 우리는 3번 방식으로 사용해서 DI
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    // 로그인 단계
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // username으로 DB에 해당 UserEntity 가 있는지 확인
        // UserEntity 가 null 이면 아이디를 잘 못 친 것이니 에러를 터트린다.
        // UserEntity 가 null 이 아니면
        // UserEntity 랑 UserRoleEntity 로 UserDetail를 만들어서 리턴한다.
        UserEntity userEntity = userRepository.findById(username);

        if (userEntity == null) {
            throw new UsernameNotFoundException("아이디를 정확히 입력해주세요.");
        }
        List<UserRoleEntity> userRoleEntityList = userRoleRepository.findByUserIdx(userEntity.getIdx());
        // 권한이 모두 삭제 되었을 경우
        // if(userRoleEntityList.size() < 1){
        // throw new AuthenticationCredentialsNotFoundException("권한이 없습니다.");
        // }

        return new CustomUserDetails(userEntity, userRoleEntityList);
    }
}
