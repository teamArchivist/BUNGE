package com.bunge.member.service;


import com.bunge.member.domain.Member;

public interface MemberService {

    public int insert(Member member);

    public  Member memberinfo(String id);

    public boolean checkid(String id);

    public boolean checknick(String nick);

    public boolean checkemail(String email);

    public String findid(String name, String email);

    public boolean findpwd(String id, String name, String email);

    public boolean pwdset(Member member);
}
