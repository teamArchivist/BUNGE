package com.bunge.study.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class StudyApproval {
    private int no;
    private String sort;
    private String approvalContent;
    private String proposer;
    private String proposeDate;
    private String approvalStatus;
    private String changeStatusDate;
}
