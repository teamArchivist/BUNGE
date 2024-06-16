package com.bunge.usermain.mapper;

import com.bunge.study.domain.StudyEvent;
import com.bunge.study.domain.StudyManagement;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMainMapper {
    List<StudyManagement> selectStudyBoardByMemberId(Map<String, Object> params);
    int countStudyBoardByMemberId(String memberId);
    List<StudyEvent> selectMyEvent(Map<String, Object> params);
    int countMyEvent(Map<String, Object> params);

}