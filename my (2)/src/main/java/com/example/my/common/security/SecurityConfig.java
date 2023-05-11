package com.example.my.common.security;

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
        
        http.csrf().disable();

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
                // 모든 페이지를 인증하게 만듬
                .anyRequest()
                .authenticated());

        // formLogin과 관련된 내용
        http.formLogin(config -> config
                .loginPage("/auth/login")
                .loginProcessingUrl("/login-process")
                .usernameParameter("id")
                .passwordParameter("pw")
                // 로그인 성공 시 이동 페이지
                // 두번째 매개변수는 로그인 성공 시 항상 세팅 페이지로 이동하게 함
                .defaultSuccessUrl("/todoList", true));

        return http.build();
    }

}
