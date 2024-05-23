package com.bunge.member.service;


import com.bunge.member.domain.Member;

public interface MemberService {

    public int isId(String id);

    public int insert(Member m);

    public  Member memberinfo(String id);

    public int idcheck(String id);

    public int nickcheck(String nick);

    public int emailcheck(String email);
}
