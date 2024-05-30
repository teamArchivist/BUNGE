package com.bunge.inquiry.controller;

import com.bunge.inquiry.domain.Inquiry;
import com.bunge.inquiry.service.InquiryCommentService;
import com.bunge.inquiry.service.InquiryService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping( "/inquiry")
public class InquiryController {
    @Autowired
    private InquiryService inquiryService;



    private static final Logger logger = LoggerFactory.getLogger(InquiryController.class);

    @Autowired
    public InquiryController(InquiryService inquiryService, InquiryCommentService inquiryCommentService) {
        this.inquiryService = inquiryService;
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

       // if (typeId != null) {
       //     inquiryList = inquiryService.getInquiriesByType(typeId, limit, offset);
        //} else {
            inquiryList = inquiryService.getAllInquiries(limit, offset);
        //}

    //    logger.info(inquiryList.toString());

        mv.setViewName("inquiry/list");
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

    @GetMapping("/addform")
    public ModelAndView addInquiryForm(ModelAndView mv) {


        mv.setViewName("inquiry/add");
        return mv;
    }

    @PostMapping("/add")
    public String inquiryWrite(Inquiry inquiry/*,@RequestParam("files") List<MultipartFile> files*/) {
        inquiryService.addInquiry(inquiry);
        return "redirect:/inquiry/list";
    }
/*
    @GetMapping("/view")
    public String viewInquiry(@RequestParam("inquiryId") Long inquiryId, ModelAndView mv) {
        Inquiry inquiry = inquiryService.getView(inquiryId);
        mv.addObject("inquiry", inquiry);
        return "inquiry/view";
    }*/


    @GetMapping("/view")
    public ModelAndView viewInquiry(
            Long inquiryId, ModelAndView mv,
            HttpServletRequest request,
            @RequestHeader(value="referer", required=false)String beforeURL) {

        logger.info("referer :" + beforeURL);

        Inquiry inquiry = inquiryService.getView(inquiryId);
        logger.info(inquiry.toString());
        //board = null; //error 페이지 이동 확인하고자 임의로 지정합니다.
        if(inquiry == null) {
            logger.info("상세보기 실패");
            mv.setViewName("error/error");
            mv.addObject("url", request.getRequestURL());
            mv.addObject("message","상세보기 실패입니다.");
        } else {
            logger.info("상세보기 성공");
            mv.setViewName("inquiry/view");
            mv.addObject("inquirydata",inquiry);
        }
        return mv;
    }

}

