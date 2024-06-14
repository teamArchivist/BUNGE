package com.bunge.study.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@ToString
@Setter
@Getter
public class StudyManagement {
    private int studyboardno;
    private String studystart;
    private String studyend;
    private int studyperiod;
    private String leader_id;
    private String booktitle;
    private String studystatus;
}
