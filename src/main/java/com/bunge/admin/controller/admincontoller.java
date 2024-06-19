package com.bunge.admin.controller;

import com.bunge.admin.domain.reportmanagement;
import com.bunge.admin.service.AdminService;
import com.bunge.member.domain.Member;
import com.bunge.study.domain.StudyBoard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    private AdminService adminservice;

    @Autowired
    public admincontoller(AdminService adminservice) {
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
    //신고 리스트
    @GetMapping(value = "/reportlist")
    public String reportlist(@RequestParam(defaultValue = "1") int page,
                             @RequestParam(defaultValue = "5") int limit,
                            Model model) {
        Map<String , Object> map = new HashMap<>();
        int offset = (page -1)* limit;
        try {
            List<reportmanagement> reportlist = adminservice.getreportlist(limit , offset);
            int reportcount = adminservice.getreportlistcount();

            model.addAttribute("reportlist", reportlist);
            model.addAttribute("reportcount", reportcount);
        } catch (Exception e) {
            map.put("message", "목록 불러오는데 실패");
        }
        return "admin/adminreportlist";
    }

    //신고자 신고내용 리스트
    @ResponseBody
    @GetMapping(value = "/reporterlist")
    public List<reportmanagement> reporterlist(@RequestParam("reporterid") String reporterid) {
        List<reportmanagement>  reporterlist = adminservice.memberreportlist(reporterid);
        return reporterlist;
    }


    @PostMapping(value = "/update-process")
    public ModelAndView reportprocess(@RequestParam(value = "reporterid") String reporterid,
                                      @RequestParam(value = "report") String reportstatus,
                                      ModelAndView mav, RedirectAttributes redirectAttributes){

        reportmanagement report = new reportmanagement();
        report.setReporterid(reporterid);
        report.setReportstatus(reportstatus);

        adminservice.updateReport(report);
        redirectAttributes.addFlashAttribute("message","처리완료");
        logger.info(reportstatus);
        mav.setViewName("redirect:adminmain");
        return mav;
    }

}
