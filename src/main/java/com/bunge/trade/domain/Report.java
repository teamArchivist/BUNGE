package com.bunge.trade.domain;

import lombok.Data;

@Data
public class Report {
    private int reportId;
    private int tradeNo;
    private String reason;
    private String details;

}
