package com.bunge.review.service;

import com.bunge.memo.domain.Book;
import com.bunge.review.domain.Review;
import com.bunge.review.mapper.ReviewMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    public Book getBookByReview(Review review) {
        return reviewMapper.getBookByReview(review);
    }

    @Override
    public void updateReview(Review review) {
        reviewMapper.updateReview(review);
    }


}
