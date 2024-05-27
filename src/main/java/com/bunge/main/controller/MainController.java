package com.bunge.main.controller;

import com.bunge.review.service.ReviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/main")
public class MainController {
    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    private final ReviewService reviewService;

    @Autowired
    public MainController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @RequestMapping
    public ModelAndView showMainPage() {
        logger.info("Accessing main page");

        ModelAndView mv = new ModelAndView();
        mv.setViewName("main");

        return mv;
    }
}
