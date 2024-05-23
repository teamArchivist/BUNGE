package com.bunge.memo.mapper;

import com.bunge.memo.domain.Memo;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface MemoMapper {

    //메모 갯수
    public int getListCount();

    //메모 목록
    public List<Memo> getMemoList(HashMap<String, Integer> map);

    //메모 삽입
    public void insertMemo(Memo memo);
}
