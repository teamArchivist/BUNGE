package com.bunge.memo.controller;

import com.bunge.memo.service.MemoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

    @RequestMapping(value="/main")
    public ModelAndView memoList(@RequestParam(value="page", defaultValue="1") int page,
                                 ModelAndView mv) {

        mv.setViewName("memo/memo_mine");

        return mv;

    }

}
