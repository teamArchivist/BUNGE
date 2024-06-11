package com.bunge.study.service;

import com.bunge.memo.domain.Book;
import com.bunge.study.domain.Study;
import com.bunge.study.mapper.StudyMapper;
import com.bunge.study.parameter.BookSearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public void createStudyBoard(Study study) {
        studyMapper.createStudyBoard(study);
    }
}
