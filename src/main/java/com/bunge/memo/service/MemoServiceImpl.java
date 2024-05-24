package com.bunge.memo.service;

import com.bunge.memo.domain.Memo;
import com.bunge.memo.mapper.MemoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemoServiceImpl implements MemoService {

    private MemoMapper memoMapper;

    @Autowired
    public MemoServiceImpl(MemoMapper memoMapper) {
        this.memoMapper = memoMapper;
    }

    @Override
    public void addMemo(Memo memo) {
        memoMapper.addMemo(memo);
    }


}