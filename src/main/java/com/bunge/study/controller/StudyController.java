package com.bunge.study.controller;

import com.bunge.memo.domain.Book;
import com.bunge.study.filter.StudyBoardFilter;
import com.bunge.study.domain.StudyBoard;
import com.bunge.study.parameter.BookSearchRequest;
import com.bunge.study.service.StudyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

        logger.info(studyBoardFilter.toString());

        int pageSize = 12;
        int offset = (page - 1) * pageSize;
        studyBoardFilter.setPage(page);
        studyBoardFilter.setOffset(offset);
        studyBoardFilter.setLimit(pageSize);

        List<StudyBoard> studyBoardList = studyService.getStudyList(studyBoardFilter);
        logger.info(studyBoardList.toString());

        model.addAttribute("studyBoardList", studyBoardList);

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
        logger.info("Received StudyBoard: " + studyBoard.toString());
        studyService.createStudyBoard(studyBoard);

        return "redirect:main";
    }
}
