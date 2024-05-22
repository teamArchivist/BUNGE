package com.bunge.member;

import com.bunge.member.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.lang.reflect.Method;
import java.security.Principal;

@Controller
@RequestMapping(value = "/member")
public class MemberController {
    private  static  final Logger logger = LoggerFactory.getLogger((MemberController.class));

    private MemberService   memberservice;
    private PasswordEncoder passwordEncoder;


    @Autowired
    public MemberController(MemberService memberservice, PasswordEncoder passwordEncoder) {
        this.memberservice=memberservice;
        this.passwordEncoder=passwordEncoder;
    }
    //로그인페이지
    @GetMapping(value="/login")
    public ModelAndView login(ModelAndView mav,
                              @CookieValue(value = "remember-me", required = false) Cookie readCookie,
                              HttpSession session,
                              Principal userPrincipal) {
        if (readCookie != null) {
            mav.setViewName("readirect:/member/index");
        } else {
            mav.setViewName("member/login");

        mav.addObject("loginfail", session.getAttribute("loginfail"));
        session.removeAttribute("loginfail");
        }
        return mav;
    }
    //회원가입
    @RequestMapping(value="/join" , method = RequestMethod.GET)
    public String join() {
        return "member/joinForm";
    }

}
