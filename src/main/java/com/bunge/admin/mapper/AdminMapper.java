package com.bunge.admin.mapper;

import com.bunge.admin.domain.adminReportListFile;
import com.bunge.admin.domain.reportmanagement;
import com.bunge.member.domain.Member;
import com.bunge.study.domain.StudyBoard;
import org.apache.ibatis.annotations.Mapper;
import com.bunge.admin.domain.Visitor;

import java.util.List;
import java.util.Map;

@Mapper
public interface AdminMapper {

    Visitor findByUsername(String username);

    void insertVisitor(Visitor visitor);

    void updateVisitor(Visitor visitor);

    int getVisitorCount();

    int getjoinCount();

    int getstudyCount();

    int getreviewCount();

    List<Member> getmemberlist(Map<String, Object> map);

    List<StudyBoard> getstudylist();

    void updateReport(reportmanagement report);

    List<reportmanagement> getreportlist(adminReportListFile adminreportlistfile);

    int  getreportlistcount(adminReportListFile adminreportlistfile);

    List<reportmanagement> memberreportlist(String reporterid);
}
