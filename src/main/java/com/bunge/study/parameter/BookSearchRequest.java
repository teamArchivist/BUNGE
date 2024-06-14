package com.bunge.study.parameter;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class BookSearchRequest {
    private String title;
    private String author;

}
