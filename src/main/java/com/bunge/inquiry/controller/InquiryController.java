package com.bunge.inquiry.controller;

import com.bunge.inquiry.domain.Inquiry;
import com.bunge.inquiry.domain.InquiryAttachment;
import com.bunge.inquiry.service.InquiryAttachmentService;
import com.bunge.inquiry.service.InquiryCommentService;
import com.bunge.inquiry.service.InquiryService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.UUID;

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

        if (typeId != null) {
            inquiryList = inquiryService.getInquiriesByType(typeId, limit, offset);
        } else {
            inquiryList = inquiryService.getAllInquiries(limit, offset);
        }

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
        // 현재 로그인된 사용자 정보 가져오기
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        String memberId = user.getUsername(); // 필요한 사용자 정보를 가져옵니다.

        mv.setViewName("inquiry/add");
        mv.addObject("memberId", memberId);
        return mv;
    }

    @PostMapping("/add")
    public String inquiryWrite(Inquiry inquiry, @RequestParam("files") List<MultipartFile> files) {
        // 현재 로그인된 사용자 정보 가져오기
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        String memberId = user.getUsername(); // 필요한 사용자 정보를 가져옵니다.

        // Inquiry 객체에 로그인된 사용자 ID 설정
        inquiry.setMemberId(memberId);

        inquiryService.addInquiry(inquiry, files);
        return "redirect:/inquiry/list";
    }

/*
    //문의글 작성 폼
    @GetMapping("/addform")
    public String addInquiryForm() {
        return "inquiry/add"; // inquiry/add.html 파일이 있어야 합니다.
    }

    @PostMapping("/add")
    public String addInquiry(Inquiry inquiry, @RequestParam("files") List<MultipartFile> files, HttpServletRequest request) throws Exception {

        String saveFolder = request.getSession().getServletContext().getRealPath("/resources/upload");

        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                String fileName = file.getOriginalFilename();
                inquiry.setOriginalFilename(fileName);
                inquiry.setStoredFilename(fileName); // 기본 파일명을 사용합니다.

                String fileDBName = fileDBName(fileName, saveFolder);
                file.transferTo(new File(saveFolder + fileDBName));

                InquiryAttachment attachment = new InquiryAttachment();
                attachment.setInquiryId(inquiry.getInquiryId());
                attachment.setOriginalFilename(fileName);
                attachment.setStoredFilename(fileDBName);
                attachment.setFilePath(saveFolder + fileDBName);
//                inquiryAttachmentService.addAttachment(attachment);
            }
        }
//        inquiryService.createInquiry(inquiry);
        return "redirect:/inquiry/list";
    }

    private String fileDBName(String fileName, String saveFolder) {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int date = c.get(Calendar.DATE);

        String homedir = saveFolder + "/" + year + "-" + month + "-" + date;
        File path1 = new File(homedir);
        if (!path1.exists()) {
            path1.mkdirs();
        }

        Random r = new Random();
        int random = r.nextInt(100000000);

        int index = fileName.lastIndexOf(".");
        String fileExtension = fileName.substring(index + 1);

        String refileName = "inq" + year + month + date + random + "." + fileExtension;
        String fileDBName = "/" + year + "-" + month + "-" + date + "/" + refileName;

        return fileDBName;
    }

    */
}

