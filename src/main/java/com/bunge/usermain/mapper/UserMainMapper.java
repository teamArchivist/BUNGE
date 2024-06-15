package com.bunge.usermain.mapper;

import com.bunge.study.domain.StudyBoard;
import com.bunge.study.domain.StudyManagement;
import com.bunge.study.domain.StudyMember;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMainMapper {
    List<StudyManagement> selectStudyBoardByMemberId(Map<String, Object> params);
    int countStudyBoardByMemberId(String memberId);
}