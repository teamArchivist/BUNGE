package com.bunge.study.service;

import com.bunge.memo.domain.Book;
import com.bunge.study.domain.StudyBoard;
import com.bunge.study.domain.StudyBoardComm;
import com.bunge.study.domain.StudyEvent;
import com.bunge.study.filter.StudyBoardFilter;
import com.bunge.study.parameter.BookSearchRequest;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface StudyService {

    //책 검색
    public List<Book> getSearchBook(BookSearchRequest bookSearchRequest);

    //스터디 글 생성
    public void createStudyBoard(StudyBoard studyBoard);

    //스터디 모집글 리스트
    public List<StudyBoard> getStudyList(StudyBoardFilter studyBoardFilter);

    //스터디 모집글 갯수
    public int getStudyListCount(StudyBoardFilter studyBoardFilter);

    //날짜 차이 계산 메소드
    public long calculateDateDifference(LocalDate startDate, LocalDate endDate);

    //스터디 모집글에서 설정한 일정 추가
    public void addStudyEvent(StudyEvent studyEvent);

    //스터디 모집글 세부정보
    public StudyBoard getDetailStudy(int no);

    //스터디 모집글 댓글 추가
    public void addBoardComment(StudyBoardComm studyBoardComm);

}
