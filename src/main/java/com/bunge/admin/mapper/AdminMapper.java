package com.bunge.admin.mapper;

import com.bunge.member.domain.Member;
import com.bunge.study.domain.StudyBoard;
import org.apache.ibatis.annotations.Mapper;
import com.bunge.admin.domain.Visitor;

import java.util.List;

@Mapper
public interface AdminMapper {

    Visitor findByUsername(String username);

    void insertVisitor(Visitor visitor);

    void updateVisitor(Visitor visitor);

    int getVisitorCount();

    int getjoinCount();

    int getstudyCount();

    int getreviewCount();

    List<Member> getmemberlist();

    List<StudyBoard> getstudylist();


}
