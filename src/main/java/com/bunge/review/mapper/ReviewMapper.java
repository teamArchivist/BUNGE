package com.bunge.review.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;

@Mapper
public interface ReviewMapper {

    //리뷰 갯수
    public int getListCount();

    //글 목록
    public List<Review> getReviewList(HashMap<String, Integer> map);


}
