package com.bunge.member.service;


import com.bunge.member.domain.Member;

public interface MemberService {

    public int isId(String id);

    public int insert(Member m);
}
