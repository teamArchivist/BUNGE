package com.bunge.member.service;


import com.bunge.member.domain.Member;

public interface MemberService {

    public int insert(Member m);

    public  Member memberinfo(String id);

    public boolean idcheck(String id);

    public boolean nickcheck(String nick);

    public boolean emailcheck(String email);

    String findid(String name, String email);

    boolean findpwd(String id, String name, String email);
}
