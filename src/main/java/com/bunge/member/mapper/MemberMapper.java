package com.bunge.member.mapper;


import com.bunge.member.domain.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;

@Mapper
public interface MemberMapper {

    public int insert(Member member);

    public Member checkid(String id);

    public Member checknick(String nick);

    public Member checkemail(String email);

    public Member findid(HashMap<String , String> map);

    public int findpwd(HashMap<String, String> map);

    public int pwdset(HashMap<String, String> map);
}
