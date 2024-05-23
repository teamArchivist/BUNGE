package com.bunge.member.security;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    private static final Logger logger = LoggerFactory.getLogger(LoginSuccessHandler.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        logger.info("로그인 성공 : LoginSuccessHandler");
        String username = authentication.getName();

        // 환영 메시지 생성
        String welcomeMessage = "환영합니다, " + username + "님!";

        // 세션에 환영 메시지 저장
        request.getSession().setAttribute("welcomemsg", welcomeMessage);
        String url = request.getContextPath()+"/member/index";
        response.sendRedirect(url);
    }
}
