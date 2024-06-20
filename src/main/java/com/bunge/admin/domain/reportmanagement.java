package com.bunge.admin.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
public class reportmanagement {
    private int reportno;            //신고번호
    private String reporttargetid;   //신고자
    private String reporterid;       //신고대상
    private String reportreason;     //신고사유
    private String    reportstatus;  //처벌내용
    private LocalDate reportstart;   //처벌시작일
    private LocalDate reportend;     //처벌종료일
    private Date reportdate;         //신고접수일
    private int reportprocessing;    //신고처리상태
}
