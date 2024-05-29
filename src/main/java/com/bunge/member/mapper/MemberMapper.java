package com.bunge.member.mapper;


import com.bunge.member.domain.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.Map;

@Mapper
public interface MemberMapper {
    public Member isId(String id);

    public int insert(Member m);

    public Member idcheck(String id);

    public Member nickcheck(String nick);

    public Member emailcheck(String email);

    Member findid(HashMap<String , String> map);

    Member findpwd(HashMap<String, String> map);
}
