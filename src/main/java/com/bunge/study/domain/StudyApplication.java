package com.bunge.study.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class StudyApplication {
    private int no;
    private int studyboardno;
    private String applicationid;
    private String applicationcontent;
    private String status;
    private String requestdate;
    private String responsedate;

}
