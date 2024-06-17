package com.bunge.admin.service;


import com.bunge.member.domain.Member;
import com.bunge.study.domain.StudyBoard;

import java.util.List;

public interface AdminService {

    void insertVisitor(String username);

    int getVisitorCount();

    int getjoinCount();

    int getstudyCount();

    int getreviewCount();

    List<StudyBoard> getstudylist();

    List<Member> getmemberlist();
}
