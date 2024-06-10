package com.bunge.inquiry.controller;

import com.bunge.inquiry.domain.Inquiry;
import com.bunge.inquiry.service.InquiryCommentService;
import com.bunge.inquiry.service.InquiryService;
import com.bunge.member.domain.Member;
import com.bunge.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
@RequestMapping( "/inquiry")
public class InquiryController {

    @Autowired
    private InquiryService inquiryService;
    private MemberService memberService;
  
    @Autowired
    public InquiryController(InquiryService inquiryService,
                             InquiryCommentService inquiryCommentService,
                             MemberService memberService) {
        this.inquiryService = inquiryService;
        this.memberService = memberService;
    }

    @GetMapping("/list")
    public ModelAndView inquiryList(ModelAndView mv, @RequestParam(value = "typeId", required = false) Integer typeId,
                                    @RequestParam(value="page", defaultValue="1") int page) {

        int limit = 10;
        int listcount = inquiryService.getInquiryCount(); // 총 리스트 수를 받아옴

        // 총 페이지 수
        int maxpage = (listcount + limit - 1) / limit;

        // 현재 페이지에 보여줄 시작 페이지 수
        int startpage = ((page - 1) / 10) * 10 + 1;

        // 현재 페이지에 보여줄 마지막 페이지 수
        int endpage = startpage + 10 - 1;

        if (endpage > maxpage)
            endpage = maxpage;

        List<Inquiry> inquiryList; // 리스트 받아옴

        int offset = (page - 1) * limit;

        inquiryList = inquiryService.getAllInquiries(limit, offset);


        mv.setViewName("inquiry/inquiry_list");
        mv.addObject("page", page);
        mv.addObject("maxpage", maxpage);
        mv.addObject("startpage", startpage);
        mv.addObject("endpage", endpage);
        mv.addObject("listcount", listcount);
        mv.addObject("inquiryList", inquiryList);
        mv.addObject("limit", limit);
        mv.addObject("previousPage", page > 1 ? page - 1 : 1);
        mv.addObject("nextPage", page < maxpage ? page + 1 : maxpage);

        return mv;
    }

    @GetMapping("/add-form")
    public ModelAndView addInquiryForm(ModelAndView mv) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loginId = authentication.getName();
        log.info("loginId : " + loginId);
        Member memberInfo = memberService.memberinfo(loginId);
        log.info("member:"+memberInfo);
        mv.addObject("memberInfo",memberInfo);
        mv.setViewName("inquiry/inquiry_add");
        return mv;
    }

    @PostMapping("/add")
    public String inquiryWrite(Inquiry inquiry) {
        inquiryService.addInquiry(inquiry);
    //    log.info("content={}", inquiry.getContent());
        return "redirect:list";
    }

    @GetMapping("/view")
    public ModelAndView viewInquiry(
            Long inquiryId, ModelAndView mv,
            HttpServletRequest request,
            @RequestHeader(value="referer", required=false)String beforeURL) {

        //log.info("referer :" + beforeURL);

        Inquiry inquiry = inquiryService.getView(inquiryId);
        //log.info(inquiry.toString());
        //board = null; //error 페이지 이동 확인하고자 임의로 지정합니다.
        if(inquiry == null) {
        //    log.info("상세보기 실패");
            mv.setViewName("error/error");
            mv.addObject("url", request.getRequestURL());
            mv.addObject("message","상세보기 실패입니다.");
        } else {
            logger.info("상세보기 성공");
            mv.setViewName("inquiry/view");
        //    log.info("상세보기 성공");
            mv.setViewName("inquiry/inquiry_view");
            mv.addObject("inquirydata",inquiry);
        }
        return mv;
    }

}

