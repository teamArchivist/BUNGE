package com.bunge.memo.filter;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class BookFilter {
    private String title;
    private String author;
    private String category;
    private Integer customerReviewRank;
    private Integer page;
    private String isbn13;
    private int offset;
    private int limit;

}