package com.bunge.member.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.bunge.member.domain.Member;
import com.bunge.member.mapper.MemberMapper;
@Service
class MemberServiceimpl implements MemberService{

    private MemberMapper    dao;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public MemberServiceimpl(MemberMapper dao, PasswordEncoder passwordEncoder) {
        this.dao = dao;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public int isId (String id) {
        Member rmember = dao.isId(id);
        return (rmember==null) ? -1 : 1; //-1은 아이디가 존재하지 않는 경우
    }									 // 1은 아이디가 존재하는 경우
    @Override
    public int insert(Member m) {
        return dao.insert(m);
    }

}
