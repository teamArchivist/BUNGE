package com.bunge.memo.controller;

import com.bunge.memo.domain.Memo;
import com.bunge.memo.service.MemoService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value="/memo")
public class MemoController {

    private static final Logger logger = LoggerFactory.getLogger(MemoController.class);

    private MemoService memoservice;

    @Autowired
    public MemoController(MemoService memoservice) { this.memoservice = memoservice; }

    //기록·리뷰 -> 나의 기록 눌렀을 때 처음 페이지
    @GetMapping("/mine")
    public ModelAndView memoMain(@RequestParam(value="page", defaultValue="1") int page, ModelAndView mv) {

        mv.setViewName("memo/memo_mine");

        return mv;
    }

    @PostMapping("/add")
    public String addMemo(Memo memo, HttpServletRequest request) {

        memoservice.addMemo(memo);
        logger.info(memo.toString());
        return "redirect:/mine";
    }




}
