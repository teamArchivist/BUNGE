package com.bunge.review.service;

import com.bunge.memo.domain.Book;
import com.bunge.review.domain.Review;
import com.bunge.review.domain.ReviewComm;
import com.bunge.review.domain.ReviewLike;
import com.bunge.review.filter.ReviewFilter;
import com.bunge.review.mapper.ReviewMapper;
import com.bunge.review.parameter.ReviewLikeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private ReviewMapper reviewMapper;

    @Autowired
    public ReviewServiceImpl(ReviewMapper reviewMapper) {
        this.reviewMapper = reviewMapper;
    }


    @Override
    public void addReview(Review review) {
        reviewMapper.addReview(review);
    }

    @Override
    public List<Review> getAllReviews() {
        return reviewMapper.getAllReviews();
    }

    @Override
    public List<Review> getReviewList(ReviewFilter reviewFilter) {
        List<Review> reviews = reviewMapper.getReviewList(reviewFilter);
        List<Review> result = new ArrayList<Review>();

        for (Review review : reviews) {
            review.setCountcomment(reviewMapper.getReviewCommListCount(review));
            review.setCountlike(reviewMapper.countReviewLikeByReview(review));

            result.add(review);
        }

        return result;
    }

    @Override
    public int getReviewListCount(ReviewFilter reviewFilter) {
        return reviewMapper.getReviewListCount(reviewFilter);
    }

    @Override
    public Book getBookByReview(Review review) {
        return reviewMapper.getBookByReview(review);
    }

    @Override
    public void updateReview(Review review) {
        reviewMapper.updateReview(review);
    }

    @Override
    public int deleteReview(Review review) {
        return reviewMapper.deleteReview(review);
    }

    @Override
    public int addReviewComment(ReviewComm reviewComm) {
        return reviewMapper.addReviewComment(reviewComm);
    }

    @Override
    public List<ReviewComm> getReviewCommList(Review review) {
        return reviewMapper.getReviewCommList(review);
    }

    @Override
    public int getReviewCommListCount(Review review) {
        return reviewMapper.getReviewCommListCount(review);
    }

    @Override
    public int controlReviewLike(ReviewLikeRequest reviewLikeRequest) {
        ReviewLike checkedReviewLike = reviewMapper.checkReviewLike(reviewLikeRequest);
        if (checkedReviewLike == null) {
           return reviewMapper.addReviewLike(reviewLikeRequest);
        } else {
            return (reviewMapper.deleteReviewLike(reviewLikeRequest)-1);
        }
    }

    @Override
    public int countReviewLike(ReviewLikeRequest reviewLikeRequest) {
        return reviewMapper.countReviewLike(reviewLikeRequest);
    }

    @Override
    public List<ReviewLike> checkReviewLikeList(ReviewLikeRequest reviewLikeRequest) {
        return reviewMapper.checkReviewLikeList(reviewLikeRequest);
    }

}
