package com.bunge.memo.service;

import com.bunge.memo.domain.ReadState;

public interface ReadStateService {

    //"목표" 추가
    public void addGoal(ReadState readState);

    //해당 책의 상태에 따른 유저 수
    public int countReadState(ReadState readState);
}
