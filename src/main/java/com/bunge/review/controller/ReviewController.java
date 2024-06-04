package com.bunge.review.controller;

import com.bunge.memo.domain.Book;
import com.bunge.memo.service.BookService;
import com.bunge.review.domain.Review;
import com.bunge.review.service.ReviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;

@Controller
@RequestMapping(value="/review")
public class ReviewController {

    private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);

    private ReviewService reviewService;
    private BookService bookService;

    @Autowired
    public ReviewController(ReviewService reviewservice, BookService bookService) {
        this.reviewService = reviewservice;
        this.bookService = bookService;
    }

    @GetMapping("/main")
    public ModelAndView reviewMain(ModelAndView modelAndView) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loginId = authentication.getName();
        //logger.info("loginId : " + loginId);

        List<Review> reviewList = reviewService.getAllReviews();
        logger.info(reviewList.toString());

        modelAndView.addObject("loginId", loginId);
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

    @ResponseBody
    @PostMapping("/get-modal-book")
    public Book getModalBook(@RequestBody Review review) {
        //logger.info(review.toString());
        return reviewService.getBookByReview(review);
    }

    @PostMapping("/modify-review")
    public String modifyReview(@ModelAttribute Review review) {
        logger.info(review.toString());
        reviewService.updateReview(review);
        return "redirect:main";
    }


    

}
