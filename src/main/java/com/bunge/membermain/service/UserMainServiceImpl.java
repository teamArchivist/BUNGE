package com.bunge.membermain.service;

import com.bunge.membermain.mapper.UserMainMapper;
import com.bunge.study.domain.StudyBoard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserMainServiceImpl implements UserMainService {

    @Autowired
    private UserMainMapper memberMainMapper;

    @Override
    public List<StudyBoard> selectStudyboardByMemberId(String memberId) {
        return memberMainMapper.selectStudyboardByMemberId(memberId);
    }

    @Override
    public int countStudyboardByMemberId(String memberId) {
        return memberMainMapper.countStudyboardByMemberId(memberId);
    }
}
