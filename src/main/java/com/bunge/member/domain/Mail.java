package com.bunge.member.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Mail {
    private String from="gksrmf2008@naver.com";
    private String to;
    private String subject;
    private String content;
    private String random;
}
