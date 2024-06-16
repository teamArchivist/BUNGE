package com.bunge.usermain.service;

import com.bunge.study.domain.StudyEvent;
import com.bunge.study.domain.StudyManagement;

import java.util.List;

public interface UserMainService {
        List<StudyManagement> selectStudyBoardByMemberId(String memberId, int size, int offset, String sort);
        int countStudyBoardByMemberId(String memberId);
        List<StudyEvent> selectMyEvent(String memberId, int size, int offset);
        int countMyEvent(String memberId);
}
