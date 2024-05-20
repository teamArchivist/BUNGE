package com.bunge.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceimpl implements MemberService{

    private MemberMapper dao;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public MemberServiceimpl(MemberMapper dao, PasswordEncoder passwordEncoder) {
        this.dao = dao;
        this.passwordEncoder = passwordEncoder;
    }

}
