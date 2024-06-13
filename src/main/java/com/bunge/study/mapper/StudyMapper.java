package com.bunge.study.mapper;

import com.bunge.memo.domain.Book;
import com.bunge.study.domain.StudyApplication;
import com.bunge.study.domain.StudyBoard;
import com.bunge.study.domain.StudyBoardComm;
import com.bunge.study.domain.StudyEvent;
import com.bunge.study.filter.StudyBoardFilter;
import com.bunge.study.parameter.BookSearchRequest;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface StudyMapper {

    //책 검색
    public List<Book> getSearchBook(BookSearchRequest bookSearchRequest);

    //스터디 글 생성
    public void createStudyBoard(StudyBoard studyBoard);

    //스터디 모집글 리스트
    public List<StudyBoard> getStudyList(StudyBoardFilter studyBoardFilter);

    //스터디 모집글 갯수
    public int getStudyListCount(StudyBoardFilter studyBoardFilter);

    //스터디 모집글 세부정보
    public StudyBoard getDetailStudy(int no);

    //스터디 모집글에서 설정한 일정 추가
    public void addStudyEvent(StudyEvent studyEvent);

    //스터디 모집글 댓글 추가
    public void addBoardComment(StudyBoardComm studyBoardComm);
    public void updateRefColumn(int no);

    //스터디 모집글 댓글 리스트
    public List<StudyBoardComm> getStudyCommList(int no);

    //스터디 모집글 댓글 총 리스트 수
    public int getStudyCommListCount(int no);

    //스터디 이벤트 리스트 조회
    public List<StudyEvent> getEventsByStudyBoardNo(int studyBoardNo);

    //스터디 신청
    public void applyStudy(StudyApplication studyApplication);
}
