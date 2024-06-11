package com.bunge.study;

import com.bunge.memo.domain.Book;
import com.bunge.study.domain.Study;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String studyMain(Model model) {

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

    @ResponseBody
    @PostMapping("/create-study")
    public Map<String, String> createStudy(Study study) {
        //logger.info(study.toString());
        Map<String, String> response = new HashMap<>();
        try {
            studyService.createStudyBoard(study);
            response.put("status", "success");
            response.put("message", "스터디 생성 성공");
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "스터디 생성 실패. 다시 시도해주세요.");
        }

        return response;
    }
}
