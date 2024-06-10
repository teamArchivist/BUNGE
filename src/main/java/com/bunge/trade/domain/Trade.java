package com.bunge.trade.domain;

import lombok.Data;

@Data
public class Trade {
    private int tradeNo;
    private String sellerID;
    private String title;
    private String description;
    private int price;
    private String categoryID;
    private String selling;
    private String status;
    private String conditions;
    private String tradeMethod;
    private String locations;
    private int readCount;
    private int likes;
}

