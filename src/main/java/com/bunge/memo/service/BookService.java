package com.bunge.memo.service;

import com.bunge.memo.domain.Book;
import com.bunge.memo.domain.ReadState;
import com.bunge.memo.filter.BookFilter;

import java.util.List;

public interface BookService {

    //책 등록
    public void addBook(Book book);

    //책 개수
    public int getBookListCount(BookFilter bookFilter);

    //책 목록
    public List<Book> getBookList(BookFilter bookFilter);

    //책 상세보기
    public Book getBookDetail(BookFilter bookFilter);

    //검색결과 DB 비교
    public List<Book> filterNewBooks(List<Book> books);

    //readstate에 따른 책
    public Book getMyBookByState(ReadState readState);

    //책 페이지 수 update
    public void updatePage(ReadState readState);

}
