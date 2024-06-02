package com.bunge.mypage.controller;

import com.bunge.member.controller.MemberController;
import com.bunge.member.domain.Member;
import com.bunge.mypage.service.MypageService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
@Controller
@RequestMapping(value = "/mypage")
public class MypageController {

    private  static  final Logger logger = LoggerFactory.getLogger((MemberController.class));

    private MypageService   mypageservice;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public MypageController(MypageService  mypageservice, PasswordEncoder passwordEncoder) {
        this.mypageservice = mypageservice;
        this.passwordEncoder=passwordEncoder;
    }

    //마이 페이지 폼 이동
    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/mypage")
    public ModelAndView  mypage(ModelAndView mav ,  Principal principal) {

        String id = principal.getName();
        if(id == null) {
            mav.addObject("message", "로그인 후 이용 가능합니다.");
            mav.setViewName("redirect:/login");
        }else {
            mav.setViewName("mypage/mypage");
        }
        return mav;
    }

    //내 정보 수정폼
    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/myinfo")
    public ModelAndView myinfo(ModelAndView mav , Principal principal){

        String id = principal.getName();

        if(id == null) {
            mav.addObject("message", "로그인 후 이용 가능합니다.");
            mav.setViewName("redirect:login");
            logger.info("로그인 후 이용 가능");
        }else {
            Member member = mypageservice.memberinfo(id);
            mav.setViewName("mypage/myinfo");
            mav.addObject("memberinfo", member);
        }
        return mav;
    }
    //내 정보 수정 처리
    @PostMapping(value = "/updateProcess")
    public ModelAndView updateProcess (Member member, ModelAndView mav,
                                       HttpServletRequest request,
                                       RedirectAttributes rattr) {
        boolean result = mypageservice.update(member);
        if(result) {
            mav.addObject("message" , "회원정보 수정이 완료되었습니다.");
            mav.setViewName("redirect:/update");
        }else {
            mav.addObject("message", "회원정보 수정이 실패했습니다.");
            mav.setViewName("/mypage/myinfo");
        }
        return mav;
    }

}
