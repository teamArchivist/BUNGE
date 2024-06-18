package com.bunge.study.mapper;

import com.bunge.study.domain.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NoticeMapper {
    void insertNotice(Notice notice);
    List<Notice> selectNoticesByStudyNo(int studyboardno);
    int countByStudyNo(int studyboardno);
    int getMaxNoticeNoByStudyboardNo(int studyboardno);
}
