package com.bunge.memo.service;

import com.bunge.memo.domain.ReadState;
import com.bunge.memo.mapper.ReadStateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReadStateServiceImpl implements ReadStateService {

    private ReadStateMapper readStateMapper;

    @Autowired
    public ReadStateServiceImpl(ReadStateMapper readStateMapper) {this.readStateMapper = readStateMapper;}

    @Override
    public void addGoal(ReadState readState) {
        readStateMapper.addGoal(readState);
    }
}
