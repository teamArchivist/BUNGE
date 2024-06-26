package com.bunge.mypage.service;

import com.bunge.member.domain.Member;
import com.bunge.review.domain.Review;

import java.util.List;
import java.util.Map;

public interface MypageService {

    Member memberinfo(String id);

    boolean pwdupdate(Member member);

    boolean nickupdate(Member member);

    boolean addrupdate(Member member);

    boolean phoupdate(Member member);

    boolean emailupdate(Member member);

    boolean update(Member member);

    List<Review> getMyReviewList(String id);


}
