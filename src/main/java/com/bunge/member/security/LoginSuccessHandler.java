package com.bunge.member.security;

import java.io.IOException;

import com.bunge.admin.domain.Visitor;
import com.bunge.admin.service.AdminService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private AdminService adminService;
    @Autowired
    private HttpSession  httpSession;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        logger.info("로그인 성공 : LoginSuccessHandler");
        HttpSession session = request.getSession();
        String username = authentication.getName();
        adminService.insertVisitor(username);

        String message = null;

        if(username.equals("admin")){
            message = "관리자 로그인했습니다.";
            session.setAttribute("message",message);
            String url = request.getContextPath()+"/admin/adminmain";
            response.sendRedirect(url);
        }else{
            message ="환영합니다 "+ username +"회원이 로그인하셨습니다.";
            session.setAttribute("message",message);
            String url = request.getContextPath()+"/user/main";
            response.sendRedirect(url);
        }
    }
}
