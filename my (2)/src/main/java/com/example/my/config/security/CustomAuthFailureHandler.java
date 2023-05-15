package com.example.my.config.security;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

// form 로그인을 했기 때문에
// 페이지를 리턴해 줘야한다.
// 그래서 SimpleUrlAuthenticationFailureHandler 를 상속 받았음.
public class CustomAuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    // 로그인 실패 시 처리하는 용도
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
        String errorMessage;

        if (exception instanceof UsernameNotFoundException) {
            errorMessage = exception.getMessage();
        } else {
            errorMessage = "알 수 없는 에러가 발생했습니다. 관리자에게 문의하세요.";
        }
        String encodedErrorMessage = URLEncoder.encode(errorMessage, StandardCharsets.UTF_8);
        setDefaultFailureUrl("/auth/login?error=true&message=" + encodedErrorMessage);
        super.onAuthenticationFailure(request, response, exception);
    }

}
