package com.bunge.inquiry.controller;

import com.bunge.inquiry.domain.Inquiry;
import com.bunge.inquiry.service.InquiryAttachmentService;
import com.bunge.inquiry.service.InquiryCommentService;
import com.bunge.inquiry.service.InquiryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping( "/inquiry")
public class InquiryController {
    @Autowired
    private InquiryService inquiryService;

    @Autowired
    private InquiryAttachmentService inquiryAttachmentService;

    @Autowired
    private InquiryCommentService inquiryCommentService;

    private static final Logger logger = LoggerFactory.getLogger(InquiryController.class);

    @Autowired
    public InquiryController(InquiryService inquiryService, InquiryAttachmentService inquiryAttachmentService,
                             InquiryCommentService inquiryCommentService) {
        this.inquiryService = inquiryService;
        this.inquiryAttachmentService = inquiryAttachmentService;
        this.inquiryCommentService = inquiryCommentService;
    }

    //문의게시판 문의글 조회페이지
    @GetMapping(value="/list")
    public ModelAndView inquiryList(ModelAndView mv, @RequestParam(value = "typeId", required = false) Integer typeId,
                                    @RequestParam(value="page", defaultValue="1") int page) {

            int limit =10;
            int listcount = inquiryService.getListCount();	//총 리스트 수를 받아옴

            //총 페이지 수
            int maxpage = (listcount + limit - 1) / limit;

            //현재 페이지에 보여줄 시작 페이지 수
            int startpage = ((page - 1) / 10) * 10 + 1;

            //현재 페이지에 보여줄 마지막 페이지 수
            int endpage = startpage + 10 - 1;

            if(endpage > maxpage)
                endpage = maxpage;

            List<Inquiry> inquiryList;	//리스트 받아옴

            int offset = (page - 1) * limit;

            if (typeId != null) {
                inquiryList = inquiryService.getInquiriesByType(typeId,page,limit);
            } else {
                inquiryList = inquiryService.getAllInquiries(page, limit);
            }

            mv.setViewName("inquiry/list");
            mv.addObject("page", page);
            mv.addObject("maxpage", maxpage);
            mv.addObject("startpage", startpage);
            mv.addObject("endpage", endpage);
            mv.addObject("listcount", listcount);
            mv.addObject("inquiryList", inquiryList);
            mv.addObject("limit", limit);

            return mv;
    }

    //문의글 작성 페이지
    @GetMapping("/add")
    public String addInquiryForm() {
        return "inquiry/add"; // inquiry/add.html 파일이 있어야 합니다.
    }

/*
    @RequestMapping(value="/add")
    public ModelAndView inquiryAdd(@RequestParam(value="page", defaultValue="1") int page,
                                    ModelAndView mv) {

        mv.setViewName("inquiry/add");

        return mv;

    }
 */

}
