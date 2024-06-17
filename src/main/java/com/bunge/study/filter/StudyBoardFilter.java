package com.bunge.study.filter;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class StudyBoardFilter {
    private String booktitle;
    private String leader;
    private int page;
    private int offset;
    private int limit;
}
