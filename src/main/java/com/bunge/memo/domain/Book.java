package com.bunge.memo.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class Book {

    private String isbn13;
    private String title;
    private String author;
    private String pubDate;
    private String categoryName;
    private String description;
    private String cover;
    private String regitdate;
    private int customerReviewRank;
    private int page;

}
