package com.bunge.member.controller;

import com.bunge.member.domain.Mail;
import com.bunge.member.domain.Member;
import com.bunge.member.service.MemberService;
import com.bunge.member.service.SendMail;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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
    private SendMail sendmail;

    @Autowired
    public MemberController(MemberService memberservice, PasswordEncoder passwordEncoder, SendMail sendmail) {
        this.memberservice=memberservice;
        this.passwordEncoder=passwordEncoder;
        this.sendmail=sendmail;
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
            mav.setViewName("redirect:member/index");
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
    @PostMapping(value = "/idcheck")
    public boolean idcheck(@RequestParam("id") String id) {
        return memberservice.idcheck(id);
    }
    //닉네임 검사
    @ResponseBody
    @PostMapping(value = "/nickcheck")
    public boolean nickcheck(@RequestParam("nick") String nick){
        return memberservice.nickcheck(nick);
    }
    //이메일 검사
    @ResponseBody
    @PostMapping(value ="/emailcheck")
    public boolean emailcheck(@RequestParam("email") String email){
        return memberservice.emailcheck(email);
    }
    //회원가입 폼 이동
    @GetMapping(value="/join")
    public String join() {
        return "member/join";
    }
    //회원가입
    @PreAuthorize("isAnonymous()")
    @PostMapping(value = "/joinProcess")
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
    @PostMapping(value = "/findidProcess")
    public String findidProcess(@RequestParam("name") String name,
                                      @RequestParam("email") String email, Model model, HttpServletResponse response) {
        String sendid = memberservice.findid(name, email);
        if (sendid != null) {
            model.addAttribute("message","아이디 찾기에 성공하셨습니다.");
            model.addAttribute("sendid", sendid);
            return "member/idcomplete";
        } else {
            model.addAttribute("message", "이름과 이메일 정보가 일치하지 않습니다.");
            return "member/findid";
        }
    }
    //비밀번호 폼 이동
    @PreAuthorize("isAnonymous()")
    @GetMapping(value = "/findpwd")
    public String findpwd() {return "member/findpwd";}

    //비밀번호 찾기
    @PreAuthorize("isAnonymous()")
    @PostMapping(value = "/findpwdProcess")
    public String findpwdProcess(@RequestParam("id") String id ,
                                       @RequestParam("name") String name ,
                                       @RequestParam("email") String email, Model model,
                                       HttpServletResponse response , HttpSession session ,
                                 Member member) {
       session.setAttribute("findid", id);
        boolean pwdset = memberservice.findpwd(id, name , email);
        if (!pwdset) {
            model.addAttribute("message","아이디, 이름, 이메일 정보 중 일치하지 않습니다.");
           return "member/findpwd";
        } else {
            Mail mail = new Mail();
            mail.setTo(member.getEmail());
            sendmail.sendMail(mail);
            model.addAttribute("message", "비밀번호 찾기에 성공하셨습니다.");
            model.addAttribute("pwdset", pwdset);
            return "member/pwdinfo";
        }
    }
    //
    @GetMapping(value = "/pwdset")
    public String pwdset(){return "member/pwdset";}

    //비밀번호 재설정
    @PostMapping(value = "/pwdsetProcess")
    public String pwdset(Member member,Model model, HttpServletRequest request, HttpSession session) {

        String findid = (String) session.getAttribute("findid");
        //비밀번호 암호화 추가
        String encPassword = passwordEncoder.encode(member.getPwd());
      
        member.setPwd(encPassword);
        member.setId(findid);

        boolean result = memberservice.pwdset(member);

        if(result) {
            model.addAttribute("message" ,"비밀번호가 정상적으로 변경되었습니다.");
            return "member/login";
        } else {
            model.addAttribute("message","비밀번호 변경에 실패하였습니다.");
            return "member/pwdset";
        }
    }
}
