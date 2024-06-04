package com.bunge.review.service;

import com.bunge.review.domain.Review;
import java.util.List;

public interface ReviewService {

    //리뷰 등록
    public void addReview(Review review);

    //리뷰 목록
    public List<Review> getAllReviews();
}
