package com.bunge.member.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.bunge.member.domain.Member;
import com.bunge.member.mapper.MemberMapper;
@Service
public class MemberServiceimpl implements MemberService{

    private MemberMapper    memberMapper;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public MemberServiceimpl(MemberMapper memberMapper, PasswordEncoder passwordEncoder) {
        this.memberMapper = memberMapper;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public int insert(Member m) {
        return memberMapper.insert(m);
    }
    @Override
    public Member memberinfo(String id) {
        return memberMapper.idcheck(id);
    }

    @Override
    public boolean idcheck(String id) {
        Member member = memberMapper.idcheck(id);
        if(member != null){
            return true;
        }
        return false;
    }

    @Override
    public boolean nickcheck(String nick) {
        Member member = memberMapper.nickcheck(nick);
        if(member != null){
            return true;
        }
        return false;
    }

    @Override
    public boolean emailcheck(String email) {
        Member member = memberMapper.emailcheck(email);
        if(member != null){
            return true;
        }
        return false;
    }
}