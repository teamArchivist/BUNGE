package com.bunge.admin.service;


import com.bunge.admin.domain.reportmanagement;
import com.bunge.member.domain.Member;
import com.bunge.study.domain.StudyBoard;

import java.util.List;

public interface AdminService {

    void insertVisitor(String username);

    int getVisitorCount();

    int getjoinCount();

    int getstudyCount();

    int getreviewCount();

    List<Member> getmemberlist(int limit,int offset);

    List<StudyBoard> getstudylist();

    void saveReport(reportmanagement report);

}
