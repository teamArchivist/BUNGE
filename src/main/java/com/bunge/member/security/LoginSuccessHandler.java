package com.bunge.member.security;

import java.io.IOException;

import com.bunge.admin.domain.Visitor;
import com.bunge.admin.service.AdminService;
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

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        logger.info("로그인 성공 : LoginSuccessHandler");
        String username = authentication.getName();
        adminService.insertVisitor(username);


        if(username.equals("admin")){
            String url = request.getContextPath()+"/admin/adminmain";
            response.sendRedirect(url);
        }else{
            String url = request.getContextPath()+"/member/main";
            response.sendRedirect(url);
        }
    }
}
