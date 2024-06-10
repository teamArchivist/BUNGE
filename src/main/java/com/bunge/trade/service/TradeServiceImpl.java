package com.bunge.trade.service;

import com.bunge.trade.domain.Report;
import com.bunge.trade.domain.Trade;
import com.bunge.trade.domain.TradeImage;
import com.bunge.trade.mapper.TradeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class TradeServiceImpl implements TradeService {

    private final TradeMapper dao;

    @Autowired
    public TradeServiceImpl(TradeMapper dao) {
        this.dao = dao;
    }

    @Override
    public int getListCount() {
        return dao.getListCount();
    }

    @Override
    public List<Trade> getTradeList(int page, int limit) {
        HashMap<String, Integer> map = new HashMap<>();
        int startRow = (page - 1) * limit + 1;
        int endRow = startRow + limit - 1;
        map.put("startRow", startRow);
        map.put("endRow", endRow);
        return dao.getTradeList(map);
    }

    @Override
    public int insertTrade(Trade trade) {
        dao.insertTrade(trade);
        return trade.getTradeNo();
    }

    @Override
    public void insertTradeImage(TradeImage tradeImage) {
        dao.insertTradeImage(tradeImage);
    }

    @Override
    public List<TradeImage> getTradeImages(int tradeNo) {
        return dao.getTradeImages(tradeNo);
    }

    @Override
    public List<Trade> selectTradeByCategoryID(String categoryID) {
        return dao.selectTradeByCategoryID(categoryID);
    }

    @Override
    public Trade getDetail(int tradeNo) {
        return dao.getDetail(tradeNo);
    }

    @Override
    public int setReadCountUpdate(int tradeNo) {
        return dao.setReadCountUpdate(tradeNo);
    }

    @Override
    public List<Trade> getTradesByCategory(String categoryID) {
        return dao.selectTradeByCategoryID(categoryID);
    }

    @Override
    public void updateTrade(Trade trade) {
        dao.updateTrade(trade);
    }

    @Override
    public int deleteTrade(int tradeNo) {
        return dao.deleteTrade(tradeNo);
    }

    @Override
    public Trade getTrade(int tradeNo) {
        return dao.getTrade(tradeNo);
    }

    @Override
    public void submitReport(Report report) {
        dao.insertReport(report);
    }
}
