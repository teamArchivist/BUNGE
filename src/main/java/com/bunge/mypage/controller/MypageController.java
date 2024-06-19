package com.bunge.mypage.controller;

import com.bunge.inquiry.domain.Inquiry;
import com.bunge.inquiry.service.InquiryService;
import com.bunge.member.domain.Mail;
import com.bunge.member.domain.Member;
import com.bunge.member.service.MemberService;
import com.bunge.mypage.service.MypageSendEmail;
import com.bunge.mypage.service.MypageService;
import com.bunge.mypage.service.ProfileService;
import com.bunge.review.domain.Review;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.Principal;
import java.util.*;

@Controller
@RequestMapping(value = "/mypage")
public class MypageController {

    private  static  final Logger        logger = LoggerFactory.getLogger((MypageController.class));
    private                MemberService memberservice;
    private                MypageService mypageservice;
    private                PasswordEncoder passwordEncoder;
    private MypageSendEmail mypagesendemail;
    private ProfileService profileservice;
    private InquiryService inquiryService;

    @Autowired
    public MypageController(MypageService  mypageservice, PasswordEncoder passwordEncoder,
                            MemberService memberservice, MypageSendEmail mypagesendemail,
                            ProfileService profileservice,InquiryService inquiryService) {
        this.mypageservice = mypageservice;
        this.passwordEncoder=passwordEncoder;
        this.memberservice=memberservice;
        this.mypagesendemail=mypagesendemail;
        this.profileservice=profileservice;
        this.inquiryService=inquiryService;
    }

    //마이 페이지 폼 이동
    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/main")
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
    //내정보 이메일 수정 인증처리
    @GetMapping(value = "/mymaildelivery")
    public ResponseEntity<String> maildelivery(@RequestParam("email") String email){
        Mail mail = new Mail();
        mail.setTo(email);
        mypagesendemail.mypagesendmail(mail);
        return ResponseEntity.ok(mail.getRandom());
    }

    //내 정보 수정 처리
    @PostMapping(value = "/update-process")
    public ModelAndView updateProcess(Member member, ModelAndView mav,
                                      MultipartFile uploadfile,
                                      String check,
                                      @RequestParam("id") String id,
                                      RedirectAttributes redirectAttributes) throws IOException {

        if (check != null && !check.equals("")) {
            member.setProfile_original(check);
        } else {
            profileservice.updateProfileImage(member, uploadfile);
        }
        // Update the member information
        boolean result = mypageservice.update(member);

        // Get updated member info
        Member infomember = memberservice.memberinfo(id);

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(infomember, infomember.getPassword(), infomember.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        if (result) {
            redirectAttributes.addFlashAttribute("message", "회원정보 수정이 완료되었습니다.");
            redirectAttributes.addFlashAttribute("infomember", infomember);
            mav.setViewName("redirect:myinfo");
        } else {
            redirectAttributes.addFlashAttribute("message", "회원정보 수정이 실패했습니다.");
            mav.setViewName("error/403");

    }
        return mav;
    }
    //내 리뷰 글보기
    @ResponseBody
    @PostMapping(value = "/myreview")
    public List<Review> myReview(Principal principal){

        String id = principal.getName();
        List<Review> list = mypageservice.getMyReviewList(id);
        logger.info(list.toString());

        return list;
    }
    //내 문의글
    @ResponseBody
    @PostMapping(value = "/myinquiry")
    public List<Inquiry> myinquiry(Principal principal){
        String id = principal.getName();
        List<Inquiry> list = inquiryService.getmyinquiry(id);
        logger.info(list.toString());
        return list;
    }
    @DeleteMapping(value = "/member-delete")
    public ResponseEntity<Void>  memberdelete(@PathVariable int memberId, Principal principal){
        String deletemember = principal.getName();
        memberservice.deletemember(memberId, deletemember);
        return ResponseEntity.ok().build();
    }
}
