package com.bunge.mypage.service;

import com.bunge.member.domain.Member;

public interface MypageService {

    Member memberinfo(String id);

    boolean update(Member member);
}
