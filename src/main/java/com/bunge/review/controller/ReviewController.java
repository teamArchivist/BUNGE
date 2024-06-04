package com.bunge.review.controller;

import com.bunge.review.domain.Review;
import com.bunge.review.service.ReviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;

@Controller
@RequestMapping(value="/review")
public class ReviewController {

    private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);

    private ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewservice) {
        this.reviewService = reviewservice;
    }

    @GetMapping("/main")
    public ModelAndView reviewMain(ModelAndView modelAndView) {
        List<Review> reviewList = reviewService.getAllReviews();
        logger.info(reviewList.toString());

        modelAndView.addObject("reviewList", reviewList);
        modelAndView.setViewName("review/review-main");
        return modelAndView;
    }

    @PostMapping("/add-review")
    public String addReview(@ModelAttribute Review review) {
        //logger.info(review.toString());
        reviewService.addReview(review);
        return "redirect:main";
    }


    

}
