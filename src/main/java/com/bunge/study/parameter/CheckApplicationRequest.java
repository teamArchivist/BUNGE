package com.bunge.study.parameter;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class CheckApplicationRequest {
    private int studyboardno;
    private String application_id;
}
