package com.bunge.memo.mapper;

import com.bunge.memo.domain.Memo;
import com.bunge.memo.domain.ReadState;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReadStateMapper {

    //독서 상태 추가
    public void addReadState(ReadState readState);

    //해당 책의 상태에 따른 유저 수
    public int countReadState(ReadState readState);

    //로그인한 사용자의 모든 독서상태
    public List<ReadState> getAllReadState(String loginId);

    //기록에 대응되는 독서상태 조회
    public ReadState getReadState(Memo memo);

    public int countTotalPage(ReadState readState);

    public int countReadPage(ReadState readState);

    //독서상태 업데이트
    public void updateReadState(ReadState readState);

    //독서상태 변경
    public int changeReadState(ReadState readState);

}
