package com.bunge.study.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class StudyMember {
    private int studyboardno;
    private String memberId;
    private String role;
}
