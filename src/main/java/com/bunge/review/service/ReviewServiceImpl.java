package com.bunge.review.service;

import com.bunge.review.mapper.ReviewMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewServiceImpl implements ReviewService {

    private ReviewMapper dao;

    @Autowired
    public ReviewServiceImpl(ReviewMapper dao) {
        this.dao = dao;
    }

    @Override
    public int getListCount() {
        return 0;
    }
}
