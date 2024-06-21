package com.bunge.study.service;

import com.bunge.study.domain.*;

import java.util.List;

public interface NoticeService {

        void addNotice(Notice notice);
        List<Notice> selectNoticesByStudyNo(int studyboardno, int page, int size);
        int countByStudyNo(int studyboardno);
        int getMaxNoticeNoByStudyboardNo(int studyboardno);
        Notice getNoticeById(int noticeId);
        boolean updateNotice(Notice notice, String loginId);
        boolean deleteNotice(int noticeId);
        String selectRoleByStudy(int studyboardno, String memberId);
        String getStudyEndDate(int studyboardno);

}
