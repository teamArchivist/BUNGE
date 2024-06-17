package com.bunge.study.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class StudyManagement {
    private int no;
    private int studyboardno;
    private String studystart;
    private String studyend;
    private int studyperiod;
    private String leaderId;
    private String studystatus;
    private String title;
    private String memberId;
    private String memberIds;
    private String booktitle;
    private String bookcover;
    private String categoryName;
    private String author;
    private String pubDate;
    private int page;

}
