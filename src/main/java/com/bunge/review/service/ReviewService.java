package com.bunge.review.service;

import com.bunge.memo.domain.Book;
import com.bunge.review.domain.Review;
import java.util.List;

public interface ReviewService {

    //리뷰 등록
    public void addReview(Review review);

    //리뷰 목록
    public List<Review> getAllReviews();

    //리뷰에 대응되는 책 정보 조회
    public Book getBookByReview(Review review);

    //리뷰 수정
    public void updateReview(Review review);

    //리뷰 삭제
    public int deleteReview(Review review);
}
