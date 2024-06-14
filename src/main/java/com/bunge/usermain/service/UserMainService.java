package com.bunge.usermain.service;

import com.bunge.study.domain.StudyBoard;

import java.util.List;

public interface UserMainService {
        List<StudyBoard> selectStudyboardByMemberId(String memberId);
        int countStudyboardByMemberId(String memberId);
}
