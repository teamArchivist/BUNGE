package com.bunge.review.controller;

import com.bunge.review.service.ReviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value="/review")
public class ReviewController {

    private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);

    private ReviewService reviewservice;

    @Autowired
    public ReviewController(ReviewService reviewservice) {
        this.reviewservice = reviewservice;
    }

    @RequestMapping(value="/main")
    public ModelAndView reviewList(@RequestParam(value="page", defaultValue="1") int page,
                                   ModelAndView mv) {

        mv.setViewName("review/review_main");

        return mv;

    }


/*
    @RequestMapping(value="/test", method= RequestMethod.GET)
    public ModelAndView headerTest(@RequestParam(value="page", defaultValue="1") int page,
                                   ModelAndView mv) {

        mv.setViewName("review/headertest");

        return mv;
    }
*/

}
