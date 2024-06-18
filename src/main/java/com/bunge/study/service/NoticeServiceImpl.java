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
}
