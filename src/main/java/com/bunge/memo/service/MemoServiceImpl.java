package com.bunge.memo.service;

import com.bunge.memo.domain.Memo;
import com.bunge.memo.mapper.MemoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemoServiceImpl implements MemoService {

    private MemoMapper dao;

    @Autowired
    public MemoServiceImpl(MemoMapper dao) { this.dao = dao;}

    @Override
    public int getListcount() {

        return 0;
    }

}