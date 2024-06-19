package com.bunge.study.service;

import com.bunge.study.domain.*;

import java.util.List;

public interface NoticeService {

        void addNotice(Notice notice);
        List<Notice> selectNoticesByStudyNo(int studyboardno);
        int countByStudyNo(int studyboardno);
        int getMaxNoticeNoByStudyboardNo(int studyboardno);
        Notice getNoticeById(int noticeId);
        boolean updateNotice(Notice notice);
        boolean deleteNotice(int noticeId);
        String selectRoleByStudy(int studyboardno, String memberId);
}
