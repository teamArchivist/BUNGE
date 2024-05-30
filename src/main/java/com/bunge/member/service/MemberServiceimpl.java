package com.bunge.member.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.bunge.member.domain.Member;
import com.bunge.member.mapper.MemberMapper;

import java.util.HashMap;

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
    public int insert(Member member) {
        return memberMapper.insert(member);
    }
    @Override
    public Member memberinfo(String id) {
        return memberMapper.idcheck(id);
    }

    @Override
    public boolean idcheck(String id) {
        Member member = memberMapper.idcheck(id);
        if(member != null) return true;
        return false;
    }

    @Override
    public boolean nickcheck(String nick) {
        Member member = memberMapper.nickcheck(nick);
        if(member != null) return true;
        return false;
    }

    @Override
    public boolean emailcheck(String email) {
        Member member = memberMapper.emailcheck(email);
        if(member != null) return true;
        return false;
    }

    @Override
    public String findid(String name, String email) {
        HashMap<String, String> map = new HashMap<>();
        map.put("name", name);
        map.put("email", email);
        Member memid = memberMapper.findid(map);
        if(memid != null) {
            String memberId = memid.getId();
            String findid = "";
            if(memberId.length() > 2) {
                findid = memberId.substring(0, memberId.length() - 2) + "**";
            } else {
                findid = memberId.substring(0, 1) + "**";
            }
            return findid;
        }
        return null;
    }
    @Override
    public boolean findpwd(String id, String name, String email) {
        HashMap<String, String> map = new HashMap<>();
        map.put("id", id);
        map.put("name", name);
        map.put("email", email);
        int count = memberMapper.findpwd(map);
        return count == 1;
    }

    @Override
    public boolean pwdset(Member member) {
        HashMap<String, String> map = new HashMap<>();
        map.put("pwd", member.getPwd());
        map.put("findid", member.getId());
        int result = memberMapper.pwdset(map);
        if(result ==1) return true;
        return false;
    }
}