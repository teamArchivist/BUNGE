package com.bunge.member.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.security.core.AuthenticationException;
import java.io.IOException;
@Component
public class LoginFailHandeler implements AuthenticationFailureHandler {
private static final Logger logger = LoggerFactory.getLogger(LoginFailHandeler.class);

    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException{
        HttpSession session = request.getSession();
        logger.info("로그인실패");
        session.setAttribute("loginfail","loginFilMsg");
        String url = request.getContextPath()+"member/login";
        response.sendRedirect(url);
    }
}
