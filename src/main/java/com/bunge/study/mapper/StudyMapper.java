package com.bunge.study.mapper;

import com.bunge.memo.domain.Book;
import com.bunge.study.domain.StudyApplication;
import com.bunge.study.domain.StudyBoard;
import com.bunge.study.domain.StudyBoardComm;
import com.bunge.study.domain.StudyEvent;
import com.bunge.study.filter.StudyBoardFilter;
import com.bunge.study.parameter.ApproveApplicationRequest;
import com.bunge.study.parameter.BookSearchRequest;
import com.bunge.study.parameter.CheckApplicationRequest;
import com.bunge.study.parameter.RejectApplicationRequest;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

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

    //스터디 리더의 경우 "대기" 상태 있는지를 확인
    public List<StudyBoard> getApprovalPendingStatus();

    //해당 스터디 모집글의 신청 상태를 가져옴
    public List<StudyApplication> getApplicationsByStudyBoardNo(int studyboardno);

    //신청에 대한 승인 절차
    public void approveApplication(ApproveApplicationRequest approveApplicationRequest);

    //신청에 대한 거절 절차
    public void rejectApplication(RejectApplicationRequest rejectApplicationRequest);

    //스터디 가입 신청 승인 취소
    public void cancelApprove(ApproveApplicationRequest approveApplicationRequest);

    //스터디 가입 신청 거절 취소
    public void cancelReject(RejectApplicationRequest rejectApplicationRequest);

    //스터디 가입 신청 후 승인 된 멤버
    public List<StudyApplication> getStudyMember(int studyboardno);

    //스터디 메인 페이지에서 나의 신청 정보를 보여주기
    public List<StudyApplication> getMyApplicationList(String loginId);

    //신청 여부 확인
    public int checkApplication(CheckApplicationRequest checkApplicationRequest);

    //모집일이 지났을 때 모집글 상태 업데이트
    public int updateEnrollStatus(StudyBoard studyBoard);
}
