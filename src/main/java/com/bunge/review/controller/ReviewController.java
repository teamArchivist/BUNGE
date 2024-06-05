package com.bunge.review.controller;

import com.bunge.memo.domain.Book;
import com.bunge.memo.service.BookService;
import com.bunge.review.domain.ReviewComm;
import com.bunge.review.domain.Review;
import com.bunge.review.filter.ReviewFilter;
import com.bunge.review.service.ReviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ModelAndView reviewMain(ModelAndView modelAndView,
                                   @RequestParam(value="booktitle", required=false) String booktitle,
                                   @RequestParam(value="id", required=false) String id,
                                   @RequestParam(value="linetitle", required=false) String linetitle,
                                   @RequestParam(value="content", required=false) String content,
                                   @RequestParam(value="score", required=false, defaultValue = "0") Integer score,
                                   @RequestParam(value="page", required=false, defaultValue = "1") Integer page) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loginId = authentication.getName();
        //logger.info("loginId : " + loginId);

        int pageSize = 12;
        int offset = (page - 1) * pageSize;

        ReviewFilter reviewFilter = new ReviewFilter();
        reviewFilter.setBooktitle(booktitle);
        reviewFilter.setId(id);
        reviewFilter.setLinetitle(linetitle);
        reviewFilter.setContent(content);
        reviewFilter.setScore(score);
        reviewFilter.setPage(pageSize);
        reviewFilter.setOffset(offset);
        reviewFilter.setLimit(pageSize);

        List<Review> reviewList = reviewService.getReviewList(reviewFilter);
        //logger.info(reviewList.toString());

        int totalReviews = reviewService.getReviewListCount(reviewFilter);

        int maxPage = (int) Math.ceil((double) totalReviews / pageSize );

        int startPage = Math.max(1, page-5);
        int endPage = Math.min(maxPage, page + 4);

        modelAndView.addObject("loginId", loginId);
        modelAndView.addObject("reviewList", reviewList);
        modelAndView.addObject("currentPage", page);
        modelAndView.addObject("maxPage", maxPage);
        modelAndView.addObject("startPage", startPage);
        modelAndView.addObject("endPage", endPage);

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
        //logger.info(review.toString());
        reviewService.updateReview(review);
        return "redirect:main";
    }

    @ResponseBody
    @PostMapping("/delete-review")
    public int deleteReview(@RequestBody Review review) {
        //logger.info(review.toString());
        return reviewService.deleteReview(review);
    }

    @ResponseBody
    @PostMapping("/get-all-reviews")
    public List<Review> getAllReview(ReviewFilter reviewFilter) {
        return reviewService.getReviewList(reviewFilter);
    }

    @ResponseBody
    @PostMapping("/add-comment")
    public int addReviewComment(ReviewComm reviewComm) {
        //logger.info(reviewComm.toString());
        return reviewService.addReviewComment(reviewComm);
    }

    @ResponseBody
    @PostMapping("/get-comment-list")
    public Map<String, Object> getReviewCommList(Review review) {
        List<ReviewComm> list = reviewService.getReviewCommList(review);
        int listcount = reviewService.getReviewCommListCount(review);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("list", list);
        map.put("listcount",listcount);

        return map;
    }

}
