package com.bunge.admin.service;

import com.bunge.admin.mapper.AdminMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class reportcheck {

    private static final Logger logger = LoggerFactory.getLogger(reportcheck.class);
    @Autowired
    private AdminMapper adminMapper;

    //@Scheduled(fixedDelay = 1000)	//밀리세컨드 단위를 사용하며 1초마다 "test"라는 로그가 나타납니다.
    public void test() throws Exception {
        logger.info("test");
    }
    //               초 분 시 일 달 요일
    @Scheduled(cron="0 0 0 * * *")
    public void check() throws Exception{
       adminMapper.updateeverday();
    }
}
