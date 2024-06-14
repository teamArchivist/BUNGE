package com.bunge.study.parameter;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class RejectApplicationRequest {
    private int no;
    private String status;
}
