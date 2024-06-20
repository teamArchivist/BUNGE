package com.bunge.study.mapper;

import com.bunge.study.domain.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NoticeMapper {
    void insertNotice(Notice notice);
    List<Notice> selectNoticesByStudyNo(@Param("studyboardno") int studyboardno, @Param("size") int size, @Param("offset") int offset);
    int countByStudyNo(@Param("studyboardno") int studyboardno);
    int getMaxNoticeNoByStudyboardNo(int studyboardno);
    Notice getNoticeById(int noticeId);
    int updateNotice(@Param("notice") Notice notice, @Param("loginId") String loginId);
    int deleteNotice(int noticeId);
    String selectRoleByStudy(@Param("studyboardno") int studyboardno, @Param("memberId") String memberId);
    String getStudyEndDate(@Param("studyboardno") int studyboardno);
}
