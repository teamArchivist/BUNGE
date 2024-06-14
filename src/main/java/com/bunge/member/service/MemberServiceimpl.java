package com.bunge.member.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import com.bunge.member.domain.Member;
import com.bunge.member.mapper.MemberMapper;

import java.util.HashMap;
import java.util.List;

@Service
public class MemberServiceimpl implements MemberService{

    private MemberMapper    memberMapper;


    @Autowired
    public MemberServiceimpl(MemberMapper memberMapper) {
        this.memberMapper = memberMapper;

    }
    @Override
    public int insert(Member member) throws DuplicateKeyException {
        try{
            return memberMapper.insert(member);
        }catch (DuplicateKeyException e) {
            throw new DuplicateKeyException("중복된 아이디입니다. 다시 가입해주세요", e);
        }
    }

    @Override
    public Member memberinfo(String id) {
        return memberMapper.checkid(id);
    }

    @Override
    public boolean checkid(String id) {
        Member member = memberMapper.checkid(id);
        if(member != null) return true;
        return false;
    }

    @Override
    public boolean checknick(String nick) {
        Member member = memberMapper.checknick(nick);
        if(member != null) return true;
        return false;
    }

    @Override
    public boolean checkemail(String email) {
        Member member = memberMapper.checkemail(email);
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

    @Override
    public List<Member> findMembers() {
        return memberMapper.findAll();
    }
}