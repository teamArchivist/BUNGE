package com.bunge.mypage.controller;

import com.bunge.member.controller.MemberController;
import com.bunge.member.domain.Member;
import com.bunge.member.service.MemberService;
import com.bunge.member.service.MemberServiceimpl;
import com.bunge.mypage.service.MypageService;
import com.bunge.review.domain.Review;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/mypage")
public class MypageController {

    private  static  final Logger        logger = LoggerFactory.getLogger((MemberController.class));
    private                MemberService memberservice;
    private                MypageService mypageservice;
    private                PasswordEncoder passwordEncoder;

    @Autowired
    public MypageController(MypageService  mypageservice, PasswordEncoder passwordEncoder, MemberService memberservice) {
        this.mypageservice = mypageservice;
        this.passwordEncoder=passwordEncoder;
        this.memberservice=memberservice;
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
    @GetMapping(value = "/myinfo")
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
    //내 정보 수정 비밀번호 처리
    @PostMapping(value = "/pwd-update-process")
    public ModelAndView pwdUpdateProcess(Member member , ModelAndView mav, String id) {
        String encPassword = passwordEncoder.encode(member.getPwd());
        Member infomember= mypageservice.memberinfo(id);
        member.setPwd(encPassword);
        member.setId(id);

        boolean result = mypageservice.pwdupdate(member);
        if(result) {
            mav.addObject("message", "회원정보 수정이 완료되었습니다.");
            mav.addObject("infomember",infomember);
            mav.setViewName("redirect:myinfo");
        }else {
            mav.addObject("message","회원정보 수정이 실패했습니다.");
            mav.setViewName("error/403");
        }
        return mav;
    }
    //내 정보 수정 처리
    @PostMapping(value = "/update-process")
    public ModelAndView updateProcess (Member member, ModelAndView mav, String id,
                                       RedirectAttributes redirectAttributes) {
        Member infomember= memberservice.memberinfo(id);
        boolean result = mypageservice.update(member);

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(member, member.getPassword(), member.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        if(result) {
            redirectAttributes.addFlashAttribute("message" , "회원정보 수정이 완료되었습니다.");
            redirectAttributes.addFlashAttribute("infomember", infomember);
            mav.setViewName("redirect:myinfo");
        }else {
            redirectAttributes.addFlashAttribute("message", "회원정보 수정이 실패했습니다.");
            mav.setViewName("error/403");
        }
        return mav;
    }
    @ResponseBody
    @PostMapping(value = "/myreview")
    public Map<String, Object> myReview(Principal principal){
        String id = principal.getName();
        Map<String, Object> map = new HashMap<String, Object>();
        List<Review> list = mypageservice.getMyReviewList(id);
        logger.info(list.toString());
        int listcount = mypageservice.getMyReviewListCount(id);
        map.put("list", list);
        map.put("listcount", listcount);
        return map;
    }
}
