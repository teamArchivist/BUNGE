package com.bunge.review.mapper;

import com.bunge.review.domain.Review;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface ReviewMapper {

    //리뷰 등록
    public void addReview(Review review);

    //리뷰 목록
    public List<Review> getAllReviews();

}
