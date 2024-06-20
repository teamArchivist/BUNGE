package com.bunge.member.security;

import com.bunge.member.domain.Member;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.security.core.AuthenticationException;
import java.io.IOException;
@Component
public class LoginFailHandeler implements AuthenticationFailureHandler {
private static final Logger logger = LoggerFactory.getLogger(LoginFailHandeler.class);
    private final CustomUserDatilsService customUserDatilsService;

    public LoginFailHandeler(CustomUserDatilsService customUserDatilsService) {
        this.customUserDatilsService = customUserDatilsService;
    }

    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException{
        HttpSession session = request.getSession();
        logger.info("로그인실패");

        String username = request.getParameter("username");

        String message = null;

        try{
            Member member = (Member) customUserDatilsService.loadUserByUsername(username);

            if(member.getSuspended() == 1) {
                message ="계정이 비활성화 되었습니다.";
            }else {
                message= "아이디나 비밀번호가 틀렸습니다.";
            }
        } catch (UsernameNotFoundException e) {
                message ="아이디나 비밀번호가 틀렸습니다.";
        }
        session.setAttribute("message",message);
        session.setAttribute("loginfail","loginFilMsg");
        String url = request.getContextPath()+"/member/login";
        response.sendRedirect(url);
    }
}
