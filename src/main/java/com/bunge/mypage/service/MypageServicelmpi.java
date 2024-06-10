package com.bunge.mypage.service;

import com.bunge.member.domain.Member;
import com.bunge.member.mapper.MemberMapper;
import com.bunge.review.domain.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public List<Map<String, Object>> getMyReviewList(String id) {
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        List<Review> list = membermapper.getMyReviewList(id);

        for (Review review : list) {
            Map<String, Object> map = new HashMap<String, Object>();
            int no = review.getNo();
            int countReview = membermapper.countReviewComm(no);
            int countLike = membermapper.countReviewLike(no);

            map.put("countReview", countReview);
            map.put("countLike", countLike);
            map.put("list", list);

            result.add(map);
        }

        return result;
    }

    @Override
    public int getMyReviewListCount(String id) {
        return membermapper.getMyReviewListCount(id);
    }
}

