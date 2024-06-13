package com.bunge.study.controller;

import com.bunge.memo.domain.Book;
import com.bunge.study.domain.StudyApplication;
import com.bunge.study.domain.StudyBoardComm;
import com.bunge.study.domain.StudyEvent;
import com.bunge.study.filter.StudyBoardFilter;
import com.bunge.study.domain.StudyBoard;
import com.bunge.study.parameter.BookSearchRequest;
import com.bunge.study.service.StudyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

        //logger.info(studyBoardFilter.toString());

        int pageSize = 8;
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

        StudyBoard studyBoard = studyService.getDetailStudy(no);
        //logger.info(studyBoard.toString());
        List<StudyBoardComm> studyCommList = studyService.getStudyCommList(no);
        //logger.info(studyCommList.toString());
        int countStudyComm = studyService.getStudyCommListCount(no);

        model.addAttribute("loginId", loginId);
        model.addAttribute("studyBoard", studyBoard);
        model.addAttribute("studyCommList", studyCommList);
        model.addAttribute("countStudyComm", countStudyComm);

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
        return studyService.getEventsByStudyBoardNo(studyBoardNo);
    }

    @GetMapping("/mine")
    public String mine(Model model) {

        return "study/study_mine";
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

}
