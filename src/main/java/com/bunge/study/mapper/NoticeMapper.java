package com.bunge.study.mapper;

import com.bunge.study.domain.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NoticeMapper {
    void insertNotice(Notice notice);
    List<Notice> selectNoticesByStudyNo(int studyboardno);
    int countByStudyNo(int studyboardno);
    int getMaxNoticeNoByStudyboardNo(int studyboardno);
    Notice getNoticeById(int noticeId);
    int updateNotice(Notice notice);
    int deleteNotice(int noticeId);
    String selectRoleByStudy(@Param("studyboardno") int studyboardno, @Param("memberId") String memberId);
}
