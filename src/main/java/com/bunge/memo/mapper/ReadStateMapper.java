package com.bunge.memo.mapper;

import com.bunge.memo.domain.ReadState;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReadStateMapper {

    //독서 상태 추가
    public void addReadState(ReadState readState);

    //해당 책의 상태에 따른 유저 수
    public int countReadState(ReadState readState);

}
