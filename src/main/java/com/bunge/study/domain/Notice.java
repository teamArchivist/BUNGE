package com.bunge.study.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@ToString
@Setter
@Getter
public class Notice {
    private int noticeId;
    private int noticeNo;
    private String title;
    private String content;
    private String authorId;
    private String createdAt;
    private int studyboardno;
    private String studyTitle;
    private String noticeTitle;

}
