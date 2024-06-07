package com.bunge.review.mapper;

import com.bunge.memo.domain.Book;
import com.bunge.review.domain.Review;
import com.bunge.review.domain.ReviewComm;
import com.bunge.review.domain.ReviewLike;
import com.bunge.review.filter.ReviewFilter;
import com.bunge.review.parameter.ReviewCommDeleteRequest;
import com.bunge.review.parameter.ReviewCommUpdateRequest;
import com.bunge.review.parameter.ReviewLikeRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface ReviewMapper {

    //리뷰 등록
    public void addReview(Review review);

    //전체 리뷰글 목록
    public List<Review> getAllReviews();

    //필터 적용 리뷰 목록
    public List<Review> getReviewList(ReviewFilter reviewFilter);

    //필터 적용 리뷰 목록 수
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

    //리뷰 좋아요 관련 작업
    public ReviewLike checkReviewLike(ReviewLikeRequest reviewLikeRequest);
    public int addReviewLike(ReviewLikeRequest reviewLikeRequest);
    public int deleteReviewLike(ReviewLikeRequest reviewLikeRequest);
    public int countReviewLike(ReviewLikeRequest reviewLikeRequest);
    public int countReviewLikeByReview(Review review);

    //좋아요를 누른 리뷰 리스트
    public List<ReviewLike> checkReviewLikeList(ReviewLikeRequest reviewLikeRequest);

    //리뷰 댓글 수정
    public int updateReviewComm(ReviewCommUpdateRequest reviewCommUpdateRequest);

    //리뷰 댓글 삭제
    public int deleteReviewComm(ReviewCommDeleteRequest reviewCommDeleteRequest);


}
