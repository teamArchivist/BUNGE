package com.bunge.study.service;

import com.bunge.memo.domain.Book;
import com.bunge.study.domain.Study;
import com.bunge.study.domain.StudyBoard;
import com.bunge.study.parameter.BookSearchRequest;

import java.util.List;

public interface StudyService {

    //책 검색
    public List<Book> getSearchBook(BookSearchRequest bookSearchRequest);

    //스터디 글 생성
    public void createStudyBoard(Study study);
    public void createStudyBoard(StudyBoard studyBoard);


}
