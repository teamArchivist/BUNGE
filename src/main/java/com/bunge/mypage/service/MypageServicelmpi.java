package com.bunge.mypage.service;

import com.bunge.member.domain.Member;
import com.bunge.member.mapper.MemberMapper;
import com.bunge.review.domain.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MypageServicelmpi implements MypageService {

    private MemberMapper membermapper;
    private PasswordEncoder passwordencoder;

    @Autowired
    public MypageServicelmpi(MemberMapper membermapper, PasswordEncoder passwordencoder) {
        this.membermapper = membermapper;
        this.passwordencoder = passwordencoder;
    }

    @Override
    public Member memberinfo(String id) {
        return membermapper.checkid(id);
    }

    @Override
    public boolean pwdupdate(Member member) {
        int result= membermapper.pwdupdate(member);
        if(result == 1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean update(Member member) {
        int result = membermapper.update(member);
        if(result == 1) {
            return true;
        }
        return false;
    }

    @Override
    public List<Review> getMyReviewList(String id) {
        return membermapper.getMyReviewList(id);
    }

    @Override
    public int getMyReviewListCount(String id) {
        return membermapper.getMyReviewListCount(id);
    }
}

