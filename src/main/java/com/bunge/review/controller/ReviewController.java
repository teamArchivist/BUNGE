package com.bunge.review.controller;

import com.bunge.memo.domain.Book;
import com.bunge.review.domain.ReviewComm;
import com.bunge.review.domain.Review;
import com.bunge.review.domain.ReviewLike;
import com.bunge.review.filter.ReviewFilter;
import com.bunge.review.parameter.ReviewCommDeleteRequest;
import com.bunge.review.parameter.ReviewCommUpdateRequest;
import com.bunge.review.parameter.ReviewLikeRequest;
import com.bunge.review.service.ReviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    public String reviewMain(Model model,
                             ReviewFilter reviewFilter,
                             ReviewLikeRequest reviewLikeRequest,
                             @RequestParam(value="page", required=false, defaultValue = "1") Integer page) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loginId = authentication.getName();
        //logger.info("loginId : " + loginId);
        //logger.info(reviewFilter.toString());
        //logger.info(reviewLikeRequest.toString());

        int pageSize = 12;
        int offset = (page - 1) * pageSize;

        reviewFilter.setPage(page);
        reviewFilter.setOffset(offset);
        reviewFilter.setLimit(pageSize);

        reviewLikeRequest.setId(loginId);


        List<Review> reviewList = reviewService.getReviewList(reviewFilter);
        //logger.info(reviewList.toString());
        int totalReviews = reviewService.getReviewListCount(reviewFilter);

        int maxPage = (int) Math.ceil((double) totalReviews / pageSize );
        int startPage = Math.max(1, page-5);
        int endPage = Math.min(maxPage, page + 4);

        model.addAttribute("loginId", loginId);
        model.addAttribute("reviewList", reviewList);
        model.addAttribute("currentPage", page);
        model.addAttribute("maxPage", maxPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "review/review_main";
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

    @ResponseBody
    @PostMapping("/review-like")
    public Map<String, Object> reviewLike(ReviewLikeRequest reviewLikeRequest) {
        //logger.info(reviewLikeRequest.toString());
        Map<String, Object> map = new HashMap<String, Object>();

        int result = reviewService.controlReviewLike(reviewLikeRequest);
        int likeCount = reviewService.countReviewLike(reviewLikeRequest);

        map.put("result", result);
        map.put("likeCount", likeCount);

        return map;
    }

    @ResponseBody
    @PostMapping("/check-review-like-list")
    public List<ReviewLike> checkReviewLikeList(ReviewLikeRequest reviewLikeRequest) {
        //logger.info(reviewLikeRequest.toString());
        List<ReviewLike> likedReviewList = reviewService.checkReviewLikeList(reviewLikeRequest);
        //logger.info(likedReviewList.toString());

        return likedReviewList;
    }

    @ResponseBody
    @PostMapping("/update-comm")
    public int updateComm(ReviewCommUpdateRequest reviewCommUpdateRequest) {
        //logger.info(reviewCommUpdateRequest.toString());

        return reviewService.updateReviewComm(reviewCommUpdateRequest);
    }

    @ResponseBody
    @PostMapping("/delete-comm")
    public int deleteComm(ReviewCommDeleteRequest reviewCommDeleteRequest) {
        //logger.info(reviewCommDeleteRequest.toString());
        //logger.info(review.toString());

        Map<String, Integer> map = new HashMap<String, Integer>();

        int deleteResult = reviewService.deleteReviewComm(reviewCommDeleteRequest);

        return deleteResult;
    }

}
