package com.bunge.admin.controller;

import com.bunge.admin.domain.adminReportListFile;
import com.bunge.admin.domain.reportmanagement;
import com.bunge.admin.service.AdminService;
import com.bunge.member.domain.Member;
import com.bunge.study.domain.StudyBoard;
import jakarta.servlet.http.HttpSession;
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
    public ModelAndView showMainPage( ModelAndView mav ,  HttpSession session) {
        int visitorCount = adminservice.getVisitorCount();
        int joinCount = adminservice.getjoinCount();
        int studyCount = adminservice.getstudyCount();
        int reviewCount = adminservice.getreviewCount();
        mav.addObject("visitorCount",visitorCount);
        mav.addObject("joinCount", joinCount);
        mav.addObject("studyCount", studyCount);
        mav.addObject("reviewCount", reviewCount);
        mav.addObject("message", session.getAttribute("message"));
        session.removeAttribute("message");
        mav.setViewName("adminmain");
        return mav;
    }
    @GetMapping(value = "/adminstudy")
    public String adminstudy() {return "admin/adminstudy";}

    @GetMapping(value = "/adminstudy2")
    public String adminstudy2() {return "admin/adminstudy2";}

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

    @GetMapping(value = "/adminreport")
    public String adminreport(adminReportListFile adminreportlistfile,
                              @RequestParam(defaultValue = "1") int page,
                              Model model){

        int limit = 10;
        int offset = (page -1)* limit;

        adminreportlistfile.setPage(page);
        adminreportlistfile.setOffset(offset);
        adminreportlistfile.setLimit(limit);
        try {
            List<reportmanagement> reportList = adminservice.getreportlist(adminreportlistfile);
            int reportCount = adminservice.getreportlistcount(adminreportlistfile);

            int maxPage = (int) Math.ceil((double) reportCount / limit);
            int startPage = Math.max(1, page -10);
            int endPage = Math.min(maxPage, page +10);


            model.addAttribute("reportList", reportList);
            model.addAttribute("reportCount", reportCount);
            model.addAttribute("currentPage", page);
            model.addAttribute("maxPage",maxPage);
            model.addAttribute("startPage",startPage);
            model.addAttribute("endPage",endPage);

        } catch (Exception e) {
            model.addAttribute("message", "목록 불러오는데 실패");
        }

        return "admin/adminreport";
    }
    //신고자 신고내용 리스트
    @ResponseBody
    @GetMapping(value = "/reporterlist")
    public List<reportmanagement> reporterlist(@RequestParam("reporttargetid") String reporttargetid) {
        List<reportmanagement>  reporterlist = adminservice.memberreportlist(reporttargetid);
        return reporterlist;
    }


    @PostMapping(value = "/update-process")
    public ModelAndView reportprocess(@RequestParam(value = "reporttargetid") String reporttargetid,
                                      @RequestParam(value = "report") String reportstatus,
                                      ModelAndView mav, RedirectAttributes redirectAttributes){

        reportmanagement report = new reportmanagement();
        report.setReporttargetid(reporttargetid);
        report.setReportstatus(reportstatus);

        adminservice.updateReport(report);
        redirectAttributes.addFlashAttribute("Processing","처리완료");
        mav.setViewName("redirect:adminmain");
        return mav;
    }

}
