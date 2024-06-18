package com.bunge.study.controller;

import com.bunge.memo.domain.Book;
import com.bunge.study.domain.*;
import com.bunge.study.filter.StudyBoardFilter;
import com.bunge.study.parameter.ApproveApplicationRequest;
import com.bunge.study.parameter.BookSearchRequest;
import com.bunge.study.parameter.CheckApplicationRequest;
import com.bunge.study.parameter.RejectApplicationRequest;
import com.bunge.study.service.StudyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/study")
public class StudyController {

    private static Logger logger = LoggerFactory.getLogger(StudyController.class);

    private final StudyService studyService;

    @Autowired
    public StudyController(StudyService studyService) {
        this.studyService = studyService;
    }

    @GetMapping("/main")
    public String studyMain(StudyBoardFilter studyBoardFilter,
                            @RequestParam(value = "page", defaultValue = "1") Integer page,
                            Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loginId = authentication.getName();

        logger.info(studyBoardFilter.toString());

        int pageSize = 10;
        int offset = (page - 1) * pageSize;
        studyBoardFilter.setPage(page);
        studyBoardFilter.setOffset(offset);
        studyBoardFilter.setLimit(pageSize);

        List<StudyBoard> studyBoardList = studyService.getStudyList(studyBoardFilter);
        //logger.info(studyBoardList.toString());

        int totalStudyList = studyService.getStudyListCount(studyBoardFilter);
        //logger.info(String.valueOf(totalStudyList));

        int maxPage = (int) Math.ceil((double) totalStudyList / pageSize);
        int startPage = Math.max(1, page - 5);
        int endPage = Math.min(maxPage, page + 4);

        //List<StudyApplication> myApplication = studyService.getMyApplications(loginId);

        model.addAttribute("loginId", loginId);
        model.addAttribute("studyBoardList", studyBoardList);
        model.addAttribute("currentPage", page);
        model.addAttribute("maxPage", maxPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "study/study_main";
    }

    @ResponseBody
    @PostMapping("/search-book")
    public List<Book> searchBook(BookSearchRequest bookSearchRequest) {
        //logger.info(bookSearchRequest.toString());

        List<Book> result = studyService.getSearchBook(bookSearchRequest);
        //logger.info(result.toString());
        return result;
    }

    @PostMapping("/create-study")
    public String createStudy(@ModelAttribute StudyBoard studyBoard) {
        //logger.info("Received StudyBoard: " + studyBoard.toString());
        studyService.createStudyBoard(studyBoard);

        return "redirect:main";
    }

    @GetMapping("/detail")
    public String detailStudy(@RequestParam("no") int no,
                              Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loginId = authentication.getName();
        int studyboardno = no;

        StudyBoard studyBoard = studyService.getDetailStudy(no);
        //logger.info(studyBoard.toString());
        List<StudyBoardComm> studyCommList = studyService.getStudyCommList(no);
        //logger.info(studyCommList.toString());
        int countStudyComm = studyService.getStudyCommListCount(no);

        List<StudyApplication> studyMember = studyService.getStudyMember(no);
        //logger.info(studyMember.toString());
        List<StudyEvent> studyEvents = studyService.getStudyEventList(studyboardno);


        model.addAttribute("loginId", loginId);
        model.addAttribute("studyBoard", studyBoard);
        model.addAttribute("studyCommList", studyCommList);
        model.addAttribute("countStudyComm", countStudyComm);
        model.addAttribute("studyMember", studyMember);
        model.addAttribute("studyEvents", studyEvents);


        return "study/study_detail";

    }

    @ResponseBody
    @PostMapping("/add-event")
    public Map<String, Object> addEvent(@RequestBody StudyEvent studyEvent) {
        Map<String, Object> response = new HashMap<>();
        try {
            studyService.addStudyEvent(studyEvent);
            response.put("status", "success");
            response.put("message", "일정이 추가되었습니다");
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "일정 추가에 실패했습니다");
        }

        return response;
    }

    @ResponseBody
    @PostMapping("/add-board-comment")
    public Map<String, Object> addBoardComment(StudyBoardComm studyBoardComm) {
        //logger.info(studyBoardComm.toString());
        Map<String, Object> response = new HashMap<>();
        try {
            studyService.addBoardComment(studyBoardComm);
            response.put("status", "success");
            response.put("message", "댓글 추가 성공");
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "댓글 추가 중 오류가 발생했습니다. 다시 시도해주세요");
        }

        return response;
    }

    @ResponseBody
    @PostMapping("/get-board-comment")
    public Map<String, Object> getBoardComment(@RequestParam int no) {
        //logger.info(String.valueOf(no));
        Map<String, Object> response = new HashMap<>();

        List<StudyBoardComm> studyCommList = studyService.getStudyCommList(no);
        int studyCommListCount = studyService.getStudyCommListCount(no);

        response.put("studyCommList", studyCommList);
        response.put("studyCommListCount", studyCommListCount);

        return response;
    }

    @ResponseBody
    @GetMapping("/get-events")
    public List<StudyEvent> getEvents(@RequestParam int studyBoardNo) {
        logger.info(studyService.getEventsByStudyBoardNo(studyBoardNo).toString());
        return studyService.getEventsByStudyBoardNo(studyBoardNo);
    }

    @ResponseBody
    @PostMapping("/apply-study")
    public Map<String, Object> applyStudy(StudyApplication studyApplication) {
        Map<String, Object> response = new HashMap<>();
        try {
            studyService.applyStudy(studyApplication);
            response.put("status", "success");
            response.put("message", "스터디 신청 완료");
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "스터디 신청 오류, 다시 시도해주세요");
        }

        return response;
    }

    @ResponseBody
    @GetMapping("/get-applications")
    public List<StudyApplication> getApplications(@RequestParam int studyboardno) {
        //logger.info(studyService.getApplicationsByStudyBoardNo(studyboardno).toString());
        return studyService.getApplicationsByStudyBoardNo(studyboardno);
    }

    @ResponseBody
    @PostMapping("/approve-application")
    public Map<String, Object> approveApplication(@RequestBody ApproveApplicationRequest approveApplicationRequest) {
        Map<String, Object> response = new HashMap<>();
        try {
            studyService.approveApplication(approveApplicationRequest);
            response.put("status", "success");
        } catch (Exception e) {
            response.put("status", "error");
        }

        return response;
    }

    @ResponseBody
    @PostMapping("/reject-application")
    public Map<String, Object> rejectApplication(@RequestBody RejectApplicationRequest rejectApplicationRequest) {
        Map<String, Object> response = new HashMap<>();
        try {
            studyService.rejectApplication(rejectApplicationRequest);
            response.put("status", "success");
        } catch (Exception e) {
            response.put("status", "error");
        }
        return response;
    }

    @ResponseBody
    @PostMapping("/cancel-approve")
    public Map<String, Object> cancelApprove(@RequestBody ApproveApplicationRequest approveApplicationRequest) {
        Map<String, Object> response = new HashMap<>();
        try {
            studyService.cancelApprove(approveApplicationRequest);
            response.put("status", "success");
        } catch (Exception e) {
            response.put("status", "error");
        }

        return response;
    }

    @ResponseBody
    @PostMapping("/cancel-reject")
    public Map<String, Object> cancelReject(@RequestBody RejectApplicationRequest rejectApplicationRequest) {
        Map<String, Object> response = new HashMap<>();
        try {
            studyService.cancelReject(rejectApplicationRequest);
            response.put("status", "success");
        } catch (Exception e) {
            response.put("status", "error");
        }

        return response;
    }

    @ResponseBody
    @GetMapping("/get-application-status")
    public Map<String, Object> getApplicationStatus(@RequestParam String loginId) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<StudyApplication> myApplication = studyService.getMyApplicationList(loginId);
            response.put("result", "success");
            response.put("myApplication", myApplication);
        } catch(Exception e) {
            response.put("result", "error");
        }

        return response;
    }

    @ResponseBody
    @PostMapping("check-application")
    public int checkApplication(CheckApplicationRequest checkApplicationRequest) {
        return studyService.checkApplication(checkApplicationRequest);
    }

    @ResponseBody
    @PostMapping("get-study-info")
    public StudyBoard getStudyInfo(@ModelAttribute StudyBoard studyBoard) {
        int studyBoardNo = studyBoard.getNo();
        return studyService.getDetailStudy(studyBoardNo);
    }

    @ResponseBody
    @PostMapping("/check-enddate")
    public boolean checkEnddate(@ModelAttribute StudyBoard studyBoard) {
        int studyBoardNo = studyBoard.getNo();
        StudyBoard studyboard = studyService.getDetailStudy(studyBoardNo);
        LocalDate today = LocalDate.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate endDate = LocalDate.parse(studyboard.getEnddate(), formatter);

        boolean isOver = today.isAfter(endDate);
        //logger.info("isover:" + isOver);

        return isOver;
    }

    @ResponseBody
    @PostMapping("/update-enroll-status")
    public int updateEnrollStatus(@ModelAttribute StudyBoard studyBoard) {
        return studyService.updateEnrollStatus(studyBoard);
    }

    @ResponseBody
    @PostMapping("/start-study")
    public int startStudy(@ModelAttribute StudyManagement studyManagement) {
        return studyService.startStudy(studyManagement);
    }

    @ResponseBody
    @PostMapping("/create-study-member")
    public Map<String, Object> createStudyMember(@RequestParam int studyboardno,
                                                 @RequestParam String memberIds) {

        Map<String, Object> response = new HashMap<>();
        try {
            List<String> memberIdList = Arrays.asList(memberIds.split(","));
            //logger.info(memberIdList.toString());
            studyService.createStudyMembers(studyboardno, memberIdList);
            response.put("status", "success");
        } catch (Exception e) {
            response.put("status", "error");
        }

        return response;
    }

    @ResponseBody
    @PostMapping("/check-study-status")
    public StudyManagement checkStudyStatus(@ModelAttribute StudyManagement studyManagement) {
        return studyService.checkStudyStatus(studyManagement);
    }

    @ResponseBody
    @PostMapping("/cancel-application")
    public int cancelApplication(@ModelAttribute StudyApplication studyApplication) {
        //logger.info(studyApplication.toString());
        return studyService.cancelApplication(studyApplication);
    }

    @GetMapping("/mine-list")
    public String mineList(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loginId = authentication.getName();

        List<StudyManagement> myStudyList = studyService.getMyStudyList(loginId);
        //logger.info(myStudyList.toString());

        model.addAttribute("myStudyList", myStudyList);
        return "study/study_mine_list";
    }


    @GetMapping("/mine")
    public String mine(Model model,
                       @RequestParam int studyboardno) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loginId = authentication.getName();

        //logger.info(String.valueOf(studyboardno));
        StudyManagement studyManagement = studyService.getStudyManagement(studyboardno);
        //logger.info(studyManagement.toString());
        int countApprovalReady = studyService.countApprovalReady(studyboardno);
        int countApprovalComplete = studyService.countApprovalComplete(studyboardno);
        int countApprovalReject = studyService.countApprovalReject(studyboardno);
        List<StudyApproval> studyApprovals = studyService.getStudyApprovalList(studyboardno);
        //logger.info(studyApprovals.toString());
        List<StudyEvent> studyEvents = studyService.getStudyEventList(studyboardno);
        logger.info(studyEvents.toString());


        model.addAttribute("loginId", loginId);
        model.addAttribute("studyManagement", studyManagement);
        model.addAttribute("countApprovalReady", countApprovalReady);
        model.addAttribute("countApprovalComplete", countApprovalComplete);
        model.addAttribute("countApprovalReject", countApprovalReject);
        model.addAttribute("studyApprovals", studyApprovals);
        model.addAttribute("studyEvents", studyEvents);

        return "study/study_mine";
    }

    @ResponseBody
    @PostMapping("/submit-change-book")
    public Map<String, Object> submitChangeBook(@ModelAttribute StudyApproval studyApproval) {

        //logger.info(studyApproval.toString());
        Map<String, Object> response = new HashMap<>();

        try {
            studyService.submitChangeBook(studyApproval);
            response.put("status", "success");
        } catch (Exception e) {
            response.put("status", "error");
        }

        return response;
    }

    @ResponseBody
    @GetMapping("/get-study-approval")
    public Map<String, Object> getStudyApproval(@RequestParam int studyboardno) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<StudyApproval> studyApprovals = studyService.getStudyApprovalList(studyboardno);
            response.put("result", "success");
            response.put("studyApprovals", studyApprovals);
        } catch (Exception e) {
            response.put("result", "error");
        }
        return response;
    }

    @ResponseBody
    @GetMapping("/get-approval-content")
    public Map<String, Object> getApprovalContent(@RequestParam int no) {
        Map<String, Object> response = new HashMap<>();
        try {
            StudyApproval approval = studyService.getApprovalByNo(no);
            response.put("status", "success");
            response.put("approval", approval);
        } catch (Exception e) {
            response.put("status", "error");
        }
        return response;
    }

    @ResponseBody
    @PostMapping("/accept-approval")
    public int acceptApproval(@ModelAttribute StudyApproval studyApproval) {

        return studyService.acceptApproval(studyApproval);
    }

    @ResponseBody
    @PostMapping("/reject-approval")
    public int rejectApproval(@ModelAttribute StudyApproval studyApproval) {

        return studyService.rejectApproval(studyApproval);
    }

    @ResponseBody
    @PostMapping("/delete-study-event")
    public int deleteStudyEvent(@RequestParam int no) {

        return studyService.deleteStudyEvent(no);
    }

    @ResponseBody
    @GetMapping("/search-study-list")
    public Map<String, Object> searchStudyList(StudyBoardFilter studyBoardFilter,
                                               @RequestParam(value="page", defaultValue = "1") Integer page) {

        int pageSize = 8;
        int offset = (page - 1) * pageSize;

        studyBoardFilter.setPage(page);
        studyBoardFilter.setOffset(offset);
        studyBoardFilter.setLimit(pageSize);

        List<StudyBoard> studyBoards = studyService.getStudyList(studyBoardFilter);

        int totalStudyBoards = studyService.getStudyListCount(studyBoardFilter);

        int maxPage = (int) Math.ceil((double) totalStudyBoards / pageSize);
        int startPage = Math.max(1, page - 5);
        int endPage = Math.min(maxPage, page + 4);

        Map<String, Object> response = new HashMap<>();
        response.put("studyBoards", studyBoards);
        response.put("currentPage", page);
        response.put("maxPage", maxPage);
        response.put("startPage", startPage);
        response.put("endPage", endPage);

        return response;
    }

    @ResponseBody
    @PostMapping("/delete-study")
    public int deleteStudy(@RequestParam int no) {
        return studyService.deleteStudy(no);
    }

}
