package com.bunge.admin.controller;

import com.bunge.admin.domain.reportmanagement;
import com.bunge.admin.service.AdminService;
import com.bunge.member.domain.Member;
import com.bunge.member.service.MemberService;
import com.bunge.study.domain.StudyBoard;
import com.bunge.study.service.StudyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @PostMapping(value = "/report-process")
    public ModelAndView reportprocess(@RequestParam(value = "reporttargetid") String reporttargetid,
                                      @RequestParam(value = "reportid") String reportid,
                                      @RequestParam(value = "reportreason") String[] reportreasons,
                                      @RequestParam(value = "report") String reportstatus, ModelAndView mav,
                                      RedirectAttributes redirectAttributes){

        //체크박스로 선택된 신고 사유들을 하나의 문자열로 합침
        String reportreason = String.join("," ,reportreasons);

        reportmanagement report = new reportmanagement();
        report.setReporttargetid(reporttargetid);
        report.setReporterid(reportid);
        report.setReportreason(reportreason);
        report.setReportstatus(reportstatus);
        //신고 정보를 데이터베이스 저장
        adminservice.saveReport(report);
        redirectAttributes.addFlashAttribute("message","처리완료");
        mav.setViewName("redirect:adminmain");
        return mav;
    }

}
