package com.bunge.usermain.mapper;

import com.bunge.study.domain.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMainMapper {
    List<StudyManagement> selectStudyBoardByMemberId(Map<String, Object> params);
    int countStudyBoardByMemberId(String memberId);
    List<StudyEvent> selectMyEvent(Map<String, Object> params);
    int countMyEvent(Map<String, Object> params);
    List<Notice> selectNoticesByStudyNo(int studyboardno, int size, int offset);
    int countByStudyNo(int studyboardno);
    int getMaxNoticeNoByStudyboardNo(int studyboardno);
    List<Notice> selectStudiesByMemberId(@Param("memberId") String memberId);
    List<Notice> selectNoticesByStudyBoardNo(@Param("studyboardno") int studyboardno, @Param("size") int size, @Param("offset") int offset);
    int countNoticesByStudyBoardNo(@Param("studyboardno") int studyboardno);

}