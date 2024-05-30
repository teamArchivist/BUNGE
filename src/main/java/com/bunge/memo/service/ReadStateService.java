package com.bunge.memo.service;

import com.bunge.memo.domain.ReadState;

public interface ReadStateService {

    //독서상태 추가
    public void addReadState(ReadState readState);

    //해당 책의 상태에 따른 유저 수
    public int countReadState(ReadState readState);
}
