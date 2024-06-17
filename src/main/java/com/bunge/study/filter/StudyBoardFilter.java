package com.bunge.study.filter;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class StudyBoardFilter {
    private String keyword;
    private String category;
    private int page;
    private int offset;
    private int limit;
}
