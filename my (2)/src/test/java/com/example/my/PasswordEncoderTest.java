package com.example.my;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderTest {
    
    @Test
    public void test1() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        // 회원가입
        String pw = "123";
        String encodedStr = bCryptPasswordEncoder.encode(pw);
        // 회원가입 끝

        // 로그인
        String newPw = "123";
        boolean result = bCryptPasswordEncoder.matches(newPw, encodedStr);

        Assertions.assertThat(result).isTrue();
    }

}
