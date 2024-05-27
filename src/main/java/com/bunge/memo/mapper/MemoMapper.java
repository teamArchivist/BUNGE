package com.bunge.memo.mapper;

import com.bunge.memo.domain.Memo;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface MemoMapper {

    //메모 등록
    public void addMemo(Memo memo);

}
