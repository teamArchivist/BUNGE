package com.bunge.study.service;

import com.bunge.memo.domain.Book;
import com.bunge.study.domain.StudyApplication;
import com.bunge.study.domain.StudyBoard;
import com.bunge.study.domain.StudyBoardComm;
import com.bunge.study.domain.StudyEvent;
import com.bunge.study.filter.StudyBoardFilter;
import com.bunge.study.mapper.StudyMapper;
import com.bunge.study.parameter.BookSearchRequest;
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
}
