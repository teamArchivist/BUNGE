package com.bunge.memo.service;

import com.bunge.memo.domain.Memo;
import com.bunge.memo.domain.ReadState;
import com.bunge.memo.filter.MemoFilter;
import com.bunge.memo.mapper.MemoMapper;
import com.bunge.memo.mapper.ReadStateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.List;

@Service
public class MemoServiceImpl implements MemoService {

    private MemoMapper memoMapper;
    private ReadStateMapper readStateMapper;

    @Autowired
    public MemoServiceImpl(MemoMapper memoMapper, ReadStateMapper readStateMapper) {
        this.memoMapper = memoMapper;
        this.readStateMapper = readStateMapper;
    }

    @Override
    @Transactional
    public void addMemo(Memo memo) {
        memoMapper.updateReadPage(memo);
        memoMapper.addMemo(memo);

        ReadState readState = new ReadState();
        String isbn13 = memo.getIsbn13();
        String id = memo.getId();

        readState.setIsbn13(isbn13);
        readState.setId(id);

        ReadState getReadState = readStateMapper.getReadState(readState);
        if (getReadState.getReadpage() == getReadState.getTotalpage()) {
            readStateMapper.updateReadState(readState);
        }
    }

    @Override
    public List<Memo> getMyMemoList(MemoFilter memoFilter) {
        return memoMapper.getMyMemoList(memoFilter);
    }


    @Override
    public void updateMemo(Memo memo) {
        memoMapper.updateMemo(memo);
    }

    @Override
    @Transactional
    public int deleteMemo(Memo memo) {
        memoMapper.updateReadPageByDelete(memo);
        ReadState readStateByDelete = readStateMapper.getReadState(memo);
        if (readStateByDelete.getTotalpage() > readStateByDelete.getReadpage()) {
            readStateByDelete.setState("도전");
            readStateMapper.changeReadState(readStateByDelete);
        }
        int result = memoMapper.deleteMemo(memo);
        return result;
    }

    @Override
    public int getMemoListCount(MemoFilter memoFilter) {
        return memoMapper.getMemoListCount(memoFilter);
    }


}