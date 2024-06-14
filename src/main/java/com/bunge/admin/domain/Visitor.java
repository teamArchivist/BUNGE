package com.bunge.admin.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Visitor {
    private int    visitnum;
    private Date   visitdate;
    private String ipaddress;
    private String useragent;
    private String userid;

}

