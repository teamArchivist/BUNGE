package com.bunge.membermain.mapper;

import com.bunge.study.domain.StudyBoard;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMainMapper {
    List<StudyBoard> selectStudyboardByMemberId(String memberId);
    int countStudyboardByMemberId(String memberId);
}