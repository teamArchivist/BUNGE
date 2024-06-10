package com.bunge.trade.service;

import com.bunge.trade.domain.Trade;
import com.bunge.trade.domain.TradeImage;

import java.util.List;

public interface TradeService {
    int getListCount();
    List<Trade> getTradeList(int page, int limit);
    void insertTrade(Trade trade);
    void insertTradeImage(TradeImage tradeImage);
    List<TradeImage> getTradeImages(int tradeNo);
    List<Trade> selectTradeByCategoryID(String categoryID);
    Trade getDetail(int tradeNo);
    int setReadCountUpdate(int tradeNo);
}
