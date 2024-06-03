package com.bunge.memo.service;

import com.bunge.memo.domain.Memo;
import com.bunge.memo.mapper.MemoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.List;

@Service
public class MemoServiceImpl implements MemoService {

    private MemoMapper memoMapper;

    @Autowired
    public MemoServiceImpl(MemoMapper memoMapper) {
        this.memoMapper = memoMapper;
    }

    @Override
    @Transactional
    public void addMemo(Memo memo) {
        memoMapper.updateReadPage(memo);
        memoMapper.addMemo(memo);
    }

    @Override
    public List<Memo> getMyMemoList(String loginId) {
        return memoMapper.getMyMemoList(loginId);
    }

    @Override
    public void updateMemo(Memo memo) {
        memoMapper.updateMemo(memo);
    }

    @Override
    @Transactional
    public int deleteMemo(Memo memo) {
        memoMapper.updateReadPageByDelete(memo);
        int result = memoMapper.deleteMemo(memo);
        return result;
    }


}