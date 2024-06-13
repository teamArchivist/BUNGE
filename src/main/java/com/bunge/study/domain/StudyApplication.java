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
    private String application_id;
    private String application_content;
    private String status;
    private String request_date;
    private String response_date;
}
