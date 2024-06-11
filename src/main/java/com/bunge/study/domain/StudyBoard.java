package com.bunge.study.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class StudyBoard {
    private int no;
    private String id;
    private String booktitle;
    private String bookcover;
    private String title;
    private String content;
    private String startdate;
    private String enddate;
    private String challengestart;
    private String challengeend;
    private long challengeperiod;
    private int quota;
    private int state;
    private int readcount;
}
