package com.bunge.study.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class StudyBoardComm {
    private int no;
    private String id;
    private int studyboardno;
    private String content;
    private int ref;
    private int lev;
    private int seq;
    private String created;
}
