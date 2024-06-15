package com.bunge.usermain.service;

import com.bunge.study.domain.StudyManagement;
import com.bunge.usermain.mapper.UserMainMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserMainServiceImpl implements UserMainService {

    @Autowired
    private UserMainMapper userMainMapper;

    public UserMainServiceImpl(UserMainMapper userMainMapper) {
        this.userMainMapper = userMainMapper;
    }

    @Override
    public List<StudyManagement> selectStudyBoardByMemberId(String memberId, int size, int offset, String sort) {
        Map<String, Object> params = new HashMap<>();
        params.put("memberId", memberId);
        params.put("size", size);
        params.put("offset", offset);
        params.put("sort", sort);
        System.out.println(sort);
        return userMainMapper.selectStudyBoardByMemberId(params);
    }

    @Override
    public int countStudyBoardByMemberId(String memberId) {
        return userMainMapper.countStudyBoardByMemberId(memberId);
    }
}
