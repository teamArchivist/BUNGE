package com.bunge.study.mapper;

import com.bunge.memo.domain.Book;
import com.bunge.study.domain.*;
import com.bunge.study.filter.StudyBoardFilter;
import com.bunge.study.parameter.ApproveApplicationRequest;
import com.bunge.study.parameter.BookSearchRequest;
import com.bunge.study.parameter.CheckApplicationRequest;
import com.bunge.study.parameter.RejectApplicationRequest;
import org.apache.ibatis.annotations.*;

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

    //스터디 시작
    public int startStudy(StudyManagement studyManagement);
    public void insertLeaderToStudyMember(StudyManagement studyManagement);

    //해당 스터디 모집글의 스터디 도전 진행 여부 확인
    public StudyManagement checkStudyStatus(StudyManagement studyManagement);

    //신청 취소
    public int cancelApplication(StudyApplication studyApplication);

    //참여 인원 스터디 멤버 테이블에 추가
    public void insertStudyMember(@Param("studyboardno") int studyboardno, @Param("memberId") String memberId);

    //나의 스터디 리스트 조회
    public List<StudyManagement> getMyStudyList(String loginId);

    //현재 진행중인 스터디 모임 페이지
    public StudyManagement getStudyManagement(int studyboardno);

    //책 변경 제안
    public void submitChangeBook(StudyApproval studyApproval);

    //승인 현황 관련 정보
    public int countApprovalReady(int studyboardno);
    public int countApprovalComplete(int studyboardno);
    public int countApprovalReject(int studyboardno);

    //현재 진행중인 스터디 승인 관련 리스트
    public List<StudyApproval> getStudyApprovalList(int studyboardno);

    //전송된 승인 요청에 대한 정보 조회
    public StudyApproval getApprovalByNo(int no);

    //승인 후 정보 변경
    public int acceptApproval(StudyApproval studyApproval);
    public void changeAcceptedBookTitle(StudyApproval studyApproval);

    //거절 후 정보 변경
    public int rejectApproval(StudyApproval studyApproval);

    //스터디 이벤트 리스트
    public List<StudyEvent> getStudyEventList(int studyboardno);

    //스터디 이벤트 삭제
    public int deleteStudyEvent(int no);

    //스터디 모집글 삭제
    public int deleteStudy(int no);

    //스터디 목표 책 변경
    public int updateEnrollBook(StudyBoard studyBoard);

    //스터디 모집글 댓글 삭제
    public int deleteStudyComm(int no);



    //나의 스터디 리스트 조회 (검색 / 기간 경과 포함)
    public List<StudyManagement> getMyStudyListByFilter(@Param("loginId")String loginId, @Param("studyBoardFilter") StudyBoardFilter studyBoardFilter);
    public int getMyStudyListCountByFilter(@Param("loginId") String loginId, @Param("studyBoardFilter") StudyBoardFilter studyBoardFilter);
    public void updateStudyManagementStatus(StudyManagement studyManagement);

}
