package com.bunge.member.mapper;


import com.bunge.member.domain.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
    public Member isId(String id);

    public int insert(Member m);

    public Member idcheck(String id);

    public Member nickcheck(String nick);

    public Member emailcheck(String email);
}
