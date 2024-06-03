package com.bunge.memo.mapper;

import com.bunge.memo.domain.Memo;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface MemoMapper {

    //메모 등록
    public void addMemo(Memo memo);

    //읽은 페이지 업데이트 (메모 등록하면서 같이 진행)
    public void updateReadPage(Memo memo);

    //메모 목록
    public List<Memo> getMyMemoList(String loginId);

    //메모 수정
    public void updateMemo(Memo memo);

    //메모 삭제
    public int deleteMemo(Memo memo);

    //읽은 페이지 업데이트 (메모 삭제하면서 같이 진행)
    public void updateReadPageByDelete(Memo memo);

}
