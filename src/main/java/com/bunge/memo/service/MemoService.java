package com.bunge.memo.service;

import com.bunge.memo.domain.Memo;
import org.springframework.stereotype.Service;

import java.util.List;


public interface MemoService {

    //메모 등록
    public void addMemo(Memo memo);

    //메모 목록
    public List<Memo> getMyMemoList(String loginId);


}
