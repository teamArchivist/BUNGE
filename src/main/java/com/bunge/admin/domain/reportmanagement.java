package com.bunge.admin.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class reportmanagement {
    private int reportno;
    private String reporttargetid;
    private String reporterid;
    private String reportreason;
    private String reportstatus;
    private Date reportstart;
    private Date reportend;
}
