package com.bunge.memo.service;

import com.bunge.memo.domain.Memo;
import com.bunge.memo.filter.MemoFilter;
import org.springframework.stereotype.Service;

import java.util.List;


public interface MemoService {

    //메모 등록
    public void addMemo(Memo memo);

    //메모 목록
    public List<Memo> getMyMemoList(MemoFilter memoFilter);

    //메모 수정
    public void updateMemo(Memo memo);

    //메모 삭제
    public int deleteMemo(Memo memo);

    //작성한 총 메모의 수
    public int getMemoListCount(MemoFilter memoFilter);

    //월별 작성한 메모 수
    public int countMemoRecord(String formattedDate);

}
