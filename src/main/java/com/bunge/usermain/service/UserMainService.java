package com.bunge.usermain.service;

import com.bunge.study.domain.*;

import java.util.List;

public interface UserMainService {
        List<StudyManagement> selectStudyBoardByMemberId(String memberId, int size, int offset, String sort);
        int countStudyBoardByMemberId(String memberId);
        List<StudyEvent> selectMyEvent(String memberId, int size, int offset);
        int countMyEvent(String memberId);
        List<Notice> selectNoticesByStudyNo(int studyboardno, int page, int size);
        int countByStudyNo(int studyboardno);
        int getMaxNoticeNoByStudyboardNo(int studyboardno);
        List<Notice> selectStudiesByMemberId(String memberId);
        List<Notice> getNoticesByStudyBoardNo(int studyboardno, int page, int size);
        int countNoticesByStudyBoardNo(int studyboardno);
}
