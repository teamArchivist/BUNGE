package com.bunge.admin.controller;

import com.bunge.admin.service.AdminServiceimpl;
import com.bunge.member.service.MemberService;
import com.bunge.study.service.StudyService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping(value = "/admin")
public class admincontoller {
    private  static  final Logger logger = LoggerFactory.getLogger((admincontoller.class));

    private MemberService memberservice;
    private StudyService     studyService;
    private AdminServiceimpl adminservice;

    @Autowired
    public admincontoller(MemberService memberservice , StudyService  studyService,
                          AdminServiceimpl adminservice) {
        this.memberservice=memberservice;
        this.studyService=studyService;
        this.adminservice=adminservice;
    }

    @GetMapping(value = "/adminmain")
    public ModelAndView showMainPage( ModelAndView mav) {
        logger.info("Accessing main page");
        int visitorCount = adminservice.getVisitorCount();
        int joinCount = adminservice.getjoinCount();
        int studyCount = adminservice.getstudyCount();
        int reviewCount = adminservice.getreviewCount();
        logger.info(visitorCount+"수"+joinCount+"수"+studyCount+"수"+reviewCount+"수");
        mav.addObject("visitorCount",visitorCount);
        mav.addObject("joinCount", joinCount);
        mav.addObject("studyCount", studyCount);
        mav.addObject("reviewCount", reviewCount);
        mav.setViewName("adminmain");
        return mav;
    }
}
