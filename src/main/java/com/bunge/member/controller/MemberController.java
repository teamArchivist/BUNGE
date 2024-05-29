package com.bunge.member.controller;

import com.bunge.member.domain.Member;
import com.bunge.member.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.io.PrintWriter;
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
    @PreAuthorize("isAnonymous()")
    @GetMapping(value = "/index")
    public String index(Model mv, HttpServletRequest request){
        String welcomemsg = (String) request.getSession().getAttribute("welcomemsg");
        mv.addAttribute("welcomemsg" , welcomemsg);
        return "member/index";
    }
    //로그인페이지
    @PreAuthorize("isAnonymous()")
    @GetMapping (value ="/login")
    public ModelAndView login(ModelAndView mav,
                        @CookieValue(value = "remember-me", required = false) Cookie readCookie,
                        HttpSession session,
                        Principal userPrincipal)  {
        if (readCookie != null) {
            String message = "로그인에 성공하셨습니다.";
            mav.addObject("message" , message);
            mav.setViewName("readirect:/member/index");
        } else {
            String message = "아이디나 비밀번호가 틀렸습니다.";
            mav.addObject("message" , message);
            mav.setViewName("member/login");

            mav.addObject("loginfail", session.getAttribute("loginfail"));
            session.removeAttribute("loginfail");
        }
        return mav;
    }
    //아이디 검사
    @ResponseBody
    @RequestMapping(value = "/idcheck", method = RequestMethod.POST)
    public boolean idcheck(@RequestParam("id") String id) {
        return memberservice.idcheck(id);
    }
    //닉네임 검사
    @ResponseBody
    @RequestMapping(value = "/nickcheck" , method = RequestMethod.POST)
    public boolean nickcheck(@RequestParam("nick") String nick){
        return memberservice.nickcheck(nick);
    }
    //이메일 검사
    @ResponseBody
    @RequestMapping(value ="/emailcheck", method = RequestMethod.POST)
    public boolean emailcheck(@RequestParam("email") String email){
        return memberservice.emailcheck(email);
    }
    //회원가입 폼 이동
    @RequestMapping(value="/join" , method = RequestMethod.GET)
    public String join() {
        return "member/join";
    }
    //회원가입
    @PreAuthorize("isAnonymous()")
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
    @PreAuthorize("isAnonymous()")
    @GetMapping(value = "/findid")
    public String findid(){
        return "member/findid";
    }

    //아이디 찾기 결과
    @PreAuthorize("isAnonymous()")
    @RequestMapping(value = "/findidProcess", method = RequestMethod.POST)
    public ModelAndView findidProcess(@RequestParam("name") String name,
                                      @RequestParam("email") String email, ModelAndView mav, HttpServletResponse response) {
        String sendid = memberservice.findid(name, email);
        if (sendid != null) {
            String message = "아이디 찾기에 성공하셨습니다.";
            mav.addObject("message", message);
            mav.addObject("sendid", sendid);
            mav.setViewName("member/idcomplete");
        } else {
            String message = "이름과 이메일 정보가 일치하지 않습니다.";
            mav.addObject("message", message);
            mav.setViewName("member/findid");
        }
        return mav;
    }
    //비밀번호 폼 이동
    @PreAuthorize("isAnonymous()")
    @GetMapping(value = "/findpwd")
    public String findpwd() {return "member/findpwd";}

    //비밀번호 찾기
    @PreAuthorize("isAnonymous()")
    @PostMapping(value = "/findpwdProcess")
    public ModelAndView findpwdProcess(@RequestParam("id") String id ,
                                       @RequestParam("name") String name ,
                                       @RequestParam("email") String email, ModelAndView mav, HttpServletResponse response) {
       boolean pwdset = memberservice.findpwd(id, name , email);
        if (!pwdset) {
            String message = "아이디, 이름, 이메일정보 중 일치하지 않습니다.";
            mav.addObject("message", message);
            mav.setViewName("member/findpwd");
        } else {
            String message = "비밀번호 찾기에 성공하셨습니다.";
            mav.addObject("message" , message);
            mav.addObject("pwdset" , pwdset);
            mav.setViewName("member/pwdset");
        }
        return mav;
    }
    //비밀번호 재설정 폼
    public ModelAndView pwdset(Member member, ModelAndView mav) {

        return mav;
    }

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
