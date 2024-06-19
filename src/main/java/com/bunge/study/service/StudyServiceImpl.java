package com.bunge.study.service;

import com.bunge.memo.domain.Book;
import com.bunge.study.domain.*;
import com.bunge.study.filter.StudyBoardFilter;
import com.bunge.study.mapper.StudyMapper;
import com.bunge.study.parameter.ApproveApplicationRequest;
import com.bunge.study.parameter.BookSearchRequest;
import com.bunge.study.parameter.CheckApplicationRequest;
import com.bunge.study.parameter.RejectApplicationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class StudyServiceImpl implements StudyService {

    private StudyMapper studyMapper;

    @Autowired
    public StudyServiceImpl(StudyMapper studyMapper) {
        this.studyMapper = studyMapper;
    }

    @Override
    public List<Book> getSearchBook(BookSearchRequest bookSearchRequest) {
        return studyMapper.getSearchBook(bookSearchRequest);
    }

    @Override
    public void createStudyBoard(StudyBoard studyBoard) {
        LocalDate startDate = LocalDate.parse(studyBoard.getChallengestart());
        LocalDate endDate = LocalDate.parse(studyBoard.getChallengeend());

        long period = calculateDateDifference(startDate, endDate);
        studyBoard.setChallengeperiod(period);

        studyMapper.createStudyBoard(studyBoard);
    }

    @Override
    public List<StudyBoard> getStudyList(StudyBoardFilter studyBoardFilter) {
        return studyMapper.getStudyList(studyBoardFilter);
    }

    @Override
    public int getStudyListCount(StudyBoardFilter studyBoardFilter) {
        return studyMapper.getStudyListCount(studyBoardFilter);
    }

    @Override
    public long calculateDateDifference(LocalDate startDate, LocalDate endDate) {
        return ChronoUnit.DAYS.between(startDate, endDate);
    }

    @Override
    public StudyBoard getDetailStudy(int no) {
        return studyMapper.getDetailStudy(no);
    }


    @Override
    public void addStudyEvent(StudyEvent studyEvent) {
        studyMapper.addStudyEvent(studyEvent);
    }

    @Override
    @Transactional
    public void addBoardComment(StudyBoardComm studyBoardComm) {
        studyMapper.addBoardComment(studyBoardComm);
        int lastInsertId = studyBoardComm.getNo();
        studyMapper.updateRefColumn(lastInsertId);
    }

    @Override
    public List<StudyBoardComm> getStudyCommList(int no) {
        return studyMapper.getStudyCommList(no);
    }

    @Override
    public int getStudyCommListCount(int no) {
        return studyMapper.getStudyCommListCount(no);
    }

    @Override
    public List<StudyEvent> getEventsByStudyBoardNo(int studyBoardNo) {
        return studyMapper.getEventsByStudyBoardNo(studyBoardNo);
    }

    @Override
    public void applyStudy(StudyApplication studyApplication) {
        studyMapper.applyStudy(studyApplication);
    }

    @Override
    public List<StudyBoard> getApprovalPendingStatus() {
        return studyMapper.getApprovalPendingStatus();
    }

    @Override
    public List<StudyApplication> getApplicationsByStudyBoardNo(int studyboardno) {
        return studyMapper.getApplicationsByStudyBoardNo(studyboardno);
    }

    @Override
    public void approveApplication(ApproveApplicationRequest approveApplicationRequest) {
        studyMapper.approveApplication(approveApplicationRequest);
    }

    @Override
    public void rejectApplication(RejectApplicationRequest rejectApplicationRequest) {
        studyMapper.rejectApplication(rejectApplicationRequest);
    }

    @Override
    public void cancelApprove(ApproveApplicationRequest approveApplicationRequest) {
        studyMapper.cancelApprove(approveApplicationRequest);
    }

    @Override
    public void cancelReject(RejectApplicationRequest rejectApplicationRequest) {
        studyMapper.cancelReject(rejectApplicationRequest);
    }

    @Override
    public List<StudyApplication> getStudyMember(int studyboardno) {
        return studyMapper.getStudyMember(studyboardno);
    }

    @Override
    public List<StudyApplication> getMyApplicationList(String loginId) {
        return studyMapper.getMyApplicationList(loginId);
    }

    @Override
    public int checkApplication(CheckApplicationRequest checkApplicationRequest) {
        return studyMapper.checkApplication(checkApplicationRequest);
    }

    @Override
    public int updateEnrollStatus(StudyBoard studyBoard) {
        return studyMapper.updateEnrollStatus(studyBoard);
    }

    @Override
    @Transactional
    public int startStudy(StudyManagement studyManagement) {
        int result = studyMapper.startStudy(studyManagement);
        studyMapper.insertLeaderToStudyMember(studyManagement);
        return result;
    }

    @Override
    public StudyManagement checkStudyStatus(StudyManagement studyManagement) {
        return studyMapper.checkStudyStatus(studyManagement);
    }

    @Override
    public int cancelApplication(StudyApplication studyApplication) {
        return studyMapper.cancelApplication(studyApplication);
    }

    @Override
    public void createStudyMembers(int studyboardno, List<String> memberIdList) {
        for (String memberId : memberIdList) {
            studyMapper.insertStudyMember(studyboardno, memberId);
        }
    }

    @Override
    public StudyManagement getStudyManagement(int studyboardno) {
        return studyMapper.getStudyManagement(studyboardno);
    }

    @Override
    public void submitChangeBook(StudyApproval studyApproval) {
        studyMapper.submitChangeBook(studyApproval);
    }

    @Override
    public int countApprovalReady(int studyboardno) {
        return studyMapper.countApprovalReady(studyboardno);
    }

    @Override
    public int countApprovalComplete(int studyboardno) {
        return studyMapper.countApprovalComplete(studyboardno);
    }

    @Override
    public int countApprovalReject(int studyboardno) {
        return studyMapper.countApprovalReject(studyboardno);
    }

    @Override
    public List<StudyApproval> getStudyApprovalList(int studyboardno) {
        return studyMapper.getStudyApprovalList(studyboardno);
    }

    @Override
    public StudyApproval getApprovalByNo(int no) {
        return studyMapper.getApprovalByNo(no);
    }

    @Override
    @Transactional
    public int acceptApproval(StudyApproval studyApproval) {
        studyMapper.changeAcceptedBookTitle(studyApproval);
        return studyMapper.acceptApproval(studyApproval);
    }

    @Override
    public int rejectApproval(StudyApproval studyApproval) {
        return studyMapper.rejectApproval(studyApproval);
    }

    @Override
    public List<StudyEvent> getStudyEventList(int studyboardno) {
        return studyMapper.getStudyEventList(studyboardno);
    }

    @Override
    public int deleteStudyEvent(int no) {
        return studyMapper.deleteStudyEvent(no);
    }

    @Override
    public int deleteStudy(int no) {
        return studyMapper.deleteStudy(no);
    }

    @Override
    public int updateEnrollBook(StudyBoard studyBoard) {
        return studyMapper.updateEnrollBook(studyBoard);
    }

    @Override
    public int deleteStudyComm(int no) {
        return studyMapper.deleteStudyComm(no);
    }

    @Override
    public List<StudyManagement> getMyStudyList(String loginId) {
        return studyMapper.getMyStudyList(loginId);
    }

    @Override
    public List<StudyManagement> getMyStudyListByFilter(String loginId, StudyBoardFilter studyBoardFilter) {
        List<StudyManagement> result = studyMapper.getMyStudyListByFilter(loginId, studyBoardFilter);
        List<StudyManagement> updatedResults = new ArrayList<>();
        LocalDate now = LocalDate.now();

        for (StudyManagement studyManagement : result) {
            LocalDate endDate = LocalDate.parse(studyManagement.getStudyend());
            long daysUntilEnd = ChronoUnit.DAYS.between(now, endDate);

            if (daysUntilEnd < 0) {
                studyMapper.updateStudyManagementStatus(studyManagement);
            }
            updatedResults.add(studyManagement);
        }

        return updatedResults;
    }

    @Override
    public int getMyStudyListCountByFilter(String loginId, StudyBoardFilter studyBoardFilter) {
        return studyMapper.getMyStudyListCountByFilter(loginId, studyBoardFilter);
    }
}
