package com.bunge.study.service;

import com.bunge.study.domain.*;
import com.bunge.study.mapper.NoticeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private NoticeMapper noticeMapper;

    @Override
    public void addNotice(Notice notice) {
        int maxNoticeNo = noticeMapper.getMaxNoticeNoByStudyboardNo(notice.getStudyboardno());
        notice.setNoticeNo(maxNoticeNo + 1);
        noticeMapper.insertNotice(notice);
    }

    @Override
    public List<Notice> selectNoticesByStudyNo(int studyboardno) {
        return noticeMapper.selectNoticesByStudyNo(studyboardno);
    }

    @Override
    public int countByStudyNo(int studyboardno) {
        return noticeMapper.countByStudyNo(studyboardno);
    }

    @Override
    public int getMaxNoticeNoByStudyboardNo(int studyboardno) {
        return noticeMapper.getMaxNoticeNoByStudyboardNo(studyboardno);
    }

    @Override
    public Notice getNoticeById(int noticeId) {
        return noticeMapper.getNoticeById(noticeId);
    }

    @Override
    public boolean updateNotice(Notice notice) {
        System.out.println("Updating notice: " + notice); // 디버깅 로그 추가
        int result = noticeMapper.updateNotice(notice);
        System.out.println("Update result: " + result); // 디버깅 로그 추가
        return result > 0;
    }

    @Override
    public boolean deleteNotice(int noticeId) {
        return noticeMapper.deleteNotice(noticeId) > 0;
    }

    @Override
    public String selectRoleByStudy(int studyboardno, String memberId) {
        System.out.println("Getting user role for memberId: " + memberId + ", studyboardno: " + studyboardno); // 디버깅 로그 추가
        String role = noticeMapper.selectRoleByStudy(studyboardno, memberId);
        System.out.println("Retrieved role: " + role); // 디버깅 로그 추가
        return role;
    }
}
