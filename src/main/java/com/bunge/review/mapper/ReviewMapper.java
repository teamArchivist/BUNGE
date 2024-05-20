package com.bunge.review.mapper;

import com.bunge.review.domain.Review;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface ReviewMapper {

    //리뷰 갯수
    public int getListCount();

    //글 목록
    public List<Review> getReviewList(HashMap<String, Integer> map);

}
