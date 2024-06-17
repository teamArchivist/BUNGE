package com.bunge.study.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class StudyEvent {
    private int no;
    private int studyBoardNo;
    private String title;
    private String eventTitle;
    private String start;
    private String end;
    private String className;
    private String studyTitle;


}
