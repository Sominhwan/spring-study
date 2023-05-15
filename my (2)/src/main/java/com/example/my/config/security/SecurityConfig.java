package com.example.my.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // csrf
        http.csrf().disable();

        // h2
        http.authorizeHttpRequests(config -> {
            try {
                config
                        .antMatchers("/h2/**")
                        .permitAll()
                        .and()
                        .headers().frameOptions().sameOrigin();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });

        // 인가 Authorization (인증 Authentication + 권한 Authority)
        http.authorizeHttpRequests(config -> config
                // 패턴에 해당하는 주소는 허용
                .antMatchers("/auth/login", "/auth/join", "/api/*/auth/**")
                .permitAll()
                // 패턴에 해당하는 주소는 권한이 있어야만 들어갈 수 있음.
                .antMatchers("/todoList")
                .hasRole("USER")
                // 모든 페이지를 인증하게 만듬
                .anyRequest()
                .authenticated());

        // formLogin과 관련된 내용
        http.formLogin(config -> config
                // 우리가 직접 만든 로그인 페이지를 사용한다.
                .loginPage("/auth/login")
                // loginProc라 생각하면 됨.
                // 로그인 form 의 action에 넣어주면 알아서 처리함
                .loginProcessingUrl("/login-process")
                // 흔히 말하는 로그인 아이디를 시큐리티에서 username이라고 한다.
                // 우리가 해당 파라미터를 커스텀 할 수 있다.
                .usernameParameter("id")
                // 비밀번호 파라미터도 커스텀 가능하다.
                .passwordParameter("pw")
                // 로그인 실패 시 처리 핸들러
                .failureHandler(new CustomAuthFailureHandler())
                // 로그인 성공 시 이동 페이지
                // 두번째 매개변수는 로그인 성공 시 항상 세팅 페이지로 이동하게 함
                .defaultSuccessUrl("/todoList", true));

        return http.build();
    }

}
