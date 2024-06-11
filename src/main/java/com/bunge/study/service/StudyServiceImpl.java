package com.bunge.study.service;

import com.bunge.memo.domain.Book;
import com.bunge.study.domain.StudyBoard;
import com.bunge.study.filter.StudyBoardFilter;
import com.bunge.study.mapper.StudyMapper;
import com.bunge.study.parameter.BookSearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
