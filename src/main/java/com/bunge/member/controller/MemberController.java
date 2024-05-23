package com.bunge.member.controller;

import com.bunge.member.domain.Member;
import com.bunge.member.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    //임시페이지
    @GetMapping(value = "/index")
    public String index(){
        return "member/index";
    }
    //로그인페이지
    @GetMapping (value ="/login")
    public ModelAndView login(ModelAndView mav,
                        @CookieValue(value = "remember-me", required = false) Cookie readCookie,
                        HttpSession session,
                        Principal userPrincipal)  {
        if (readCookie != null) {
            mav.setViewName("readirect:/member/index");
        } else {
            mav.setViewName("member/login");

            mav.addObject("loginfail", session.getAttribute("loginfail"));
            session.removeAttribute("loginfail");
        }
        return mav;
    }
    //아이디 검사
    @ResponseBody
    @RequestMapping(value = "/idcheck", method = RequestMethod.POST)
    public int idcheck(@RequestParam("id") String id) {
        return memberservice.idcheck(id);
    }
    //닉네임 검사
    @ResponseBody
    @RequestMapping(value = "/nickcheck" , method = RequestMethod.POST)
    public int nickcheck(@RequestParam("nick") String nick){
        return memberservice.nickcheck(nick);
    }
    //이메일 검사
    @ResponseBody
    @RequestMapping(value ="/emailcheck", method = RequestMethod.POST)
    public int emailcheck(@RequestParam("email") String email){
        return memberservice.emailcheck(email);
    }
    //회원가입
    @RequestMapping(value="/join" , method = RequestMethod.GET)
    public String join() {
        return "member/join";
    }
    //회원가입
    @RequestMapping(value = "/joinProcess", method = RequestMethod.POST)
    public String joinProcess (Member member, RedirectAttributes rattr,
                               Model model, HttpServletRequest request) {
        //비밀번호 암호화 추가
        String encPassword = passwordEncoder.encode(member.getPwd());
        logger.info(encPassword);
        member.setPwd(encPassword);

        int result = memberservice.insert(member);

        // 삽입이 된 경우
        if (result == 1) {
            rattr.addFlashAttribute("result","joinSuccess");
            return "redirect:login";
        }else {
            model.addAttribute("message", "회원 가입 실패");
            model.addAttribute("url", request.getRequestURL());
            return "error/erorr";
        }
    }
    //아이디 찾기 폼 접속
    @GetMapping(value = "/findid")
    public String fiendid(){
        return "member/findid";
    };
    //회원정보 수정
    @RequestMapping(value = "/update" , method = RequestMethod.GET)
    public ModelAndView index(ModelAndView mav , Principal principal){

        String id = principal.getName();

        if(id == null) {
            mav.setViewName("redirect:login");
            logger.info("id is null");
        }else {
            Member m = memberservice.memberinfo(id);
            mav.setViewName("member/update");
            mav.addObject("memberinfo", m);
        }
        return mav;
    }
}
