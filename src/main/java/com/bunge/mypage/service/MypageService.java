package com.bunge.mypage.service;

import com.bunge.member.domain.Member;
import com.bunge.review.domain.Review;

import java.util.List;
import java.util.Map;

public interface MypageService {

    Member memberinfo(String id);

    boolean pwdupdate(Member member);

    boolean update(Member member);

    List<Map<String, Object>> getMyReviewList(String id);

    int getMyReviewListCount(String id);

}
