package com.bunge.member.mapper;


import com.bunge.member.domain.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;

@Mapper
public interface MemberMapper {
    public Member isId(String id);

    public int insert(Member member);

    public Member idcheck(String id);

    public Member nickcheck(String nick);

    public Member emailcheck(String email);

    Member findid(HashMap<String , String> map);

    int findpwd(HashMap<String, String> map);

    int pwdset(Member member);
}
