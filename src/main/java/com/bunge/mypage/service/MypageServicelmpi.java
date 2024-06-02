package com.bunge.mypage.service;

import com.bunge.member.domain.Member;
import com.bunge.member.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
        return membermapper.idcheck(id);
    }

    @Override
    public boolean update(Member member) {
        return false;
    }
}

