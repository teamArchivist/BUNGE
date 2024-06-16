package com.bunge.usermain.service;

import com.bunge.study.domain.StudyEvent;
import com.bunge.study.domain.StudyManagement;
import com.bunge.usermain.mapper.UserMainMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
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
        return userMainMapper.selectStudyBoardByMemberId(params);
    }

    @Override
    public int countStudyBoardByMemberId(String memberId) {
        return userMainMapper.countStudyBoardByMemberId(memberId);
    }

    @Override
    public List<StudyEvent> selectMyEvent(String memberId, int size, int offset) {
        Map<String, Object> params = new HashMap<>();
        params.put("memberId", memberId);
        params.put("size", size);
        params.put("offset", offset);
        log.info("Params for selectMyEvent: {}", params);
        List<StudyEvent> events = userMainMapper.selectMyEvent(params);
        log.info("Events retrieved: {}", events); // 디버깅 출력
        return events;
    }

    @Override
    public int countMyEvent(String memberId) {
        Map<String, Object> params = new HashMap<>();
        params.put("memberId", memberId);
        log.info("Params for countMyEvent: {}", params);
        int count = userMainMapper.countMyEvent(params);
        log.info("Total events count for member {}: {}", memberId, count); // 디버깅 출력
        return count;
    }

}
