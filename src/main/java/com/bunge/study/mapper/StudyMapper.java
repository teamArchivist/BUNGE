package com.bunge.study.mapper;

import com.bunge.memo.domain.Book;
import com.bunge.study.domain.StudyBoard;
import com.bunge.study.filter.StudyBoardFilter;
import com.bunge.study.parameter.BookSearchRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StudyMapper {

    //책 검색
    public List<Book> getSearchBook(BookSearchRequest bookSearchRequest);

    //스터디 글 생성
    public void createStudyBoard(StudyBoard studyBoard);

    //스터디 모집글 리스트
    public List<StudyBoard> getStudyList(StudyBoardFilter studyBoardFilter);
}
