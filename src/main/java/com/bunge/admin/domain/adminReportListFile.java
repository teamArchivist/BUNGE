package com.bunge.admin.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class adminReportListFile {

    private int page;
    private int offset;
    private int limit;
}
