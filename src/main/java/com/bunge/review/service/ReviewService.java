package com.bunge.review.service;

import com.bunge.memo.domain.Book;
import com.bunge.review.domain.Review;
import com.bunge.review.domain.ReviewComm;
import com.bunge.review.filter.ReviewFilter;

import java.util.List;

public interface ReviewService {

    //리뷰 등록
    public void addReview(Review review);

    //필터 적용 리뷰글 목록
    public List<Review> getReviewList(ReviewFilter reviewFilter);

    //필터 적용 리뷰글 목록 수
    public int getReviewListCount(ReviewFilter reviewFilter);

    //리뷰에 대응되는 책 정보 조회
    public Book getBookByReview(Review review);

    //리뷰 수정
    public void updateReview(Review review);

    //리뷰 삭제
    public int deleteReview(Review review);

    //리뷰 댓글 등록
    public int addReviewComment(ReviewComm reviewComm);

    //리뷰 댓글 목록
    public List<ReviewComm> getReviewCommList(Review review);

    //리뷰 댓글 목록 수
    public int getReviewCommListCount(Review review);
}
