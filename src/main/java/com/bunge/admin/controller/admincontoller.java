package com.bunge.admin.controller;

import com.bunge.admin.service.AdminService;
import com.bunge.admin.service.AdminServiceimpl;
import com.bunge.member.domain.Member;
import com.bunge.member.service.MemberService;
import com.bunge.memo.domain.Book;
import com.bunge.study.domain.StudyBoard;
import com.bunge.study.service.StudyService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/admin")
public class admincontoller {
    private  static  final Logger logger = LoggerFactory.getLogger((admincontoller.class));

    private MemberService memberservice;
    private StudyService studyService;
    private AdminService adminservice;

    @Autowired
    public admincontoller(MemberService memberservice , StudyService  studyService,
                          AdminService adminservice) {
        this.memberservice=memberservice;
        this.studyService=studyService;
        this.adminservice=adminservice;
    }
    //관리자페이지 웹페이지 정보
    @GetMapping(value = "/adminmain")
    public ModelAndView showMainPage( ModelAndView mav) {
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
    @GetMapping(value = "/adminstudy")
    public String adminstudy() {return "admin/adminstudy";}

    @GetMapping(value = "/adminstudy2")
    public String adminstudy2() {return "admin/adminstudy2";}

    @GetMapping(value = "/adminreport")
    public String adminreport(){return "admin/adminreport";}

    //맴버 목록
    @ResponseBody
    @GetMapping(value = "/memberlistto")
    public Map<String, Object> memberlist(@RequestParam(defaultValue = "1") int page,
                                          @RequestParam(defaultValue = "5") int limit) {

        Map<String , Object> map = new HashMap<>();
        int offset = (page -1)* limit;
        try {
            List<Member> memberlist = adminservice.getmemberlist(limit, offset);
            int memberlistcount = adminservice.getjoinCount();

            map.put("memberlist",memberlist);
            map.put("memberlistcount",memberlistcount);
            map.put("status","success");
        }catch (Exception e) {
            map.put("status","error");
            map.put("message","목록을 불러오는 실패");
            e.printStackTrace();
        }
        return map;
    }

    //스터디 목록 (진행중인 스터디)
    @ResponseBody
    @GetMapping(value = "/studylist")
    public List<StudyBoard> studylist(){
        List<StudyBoard> list = adminservice.getstudylist();
        return list;
    }

}
