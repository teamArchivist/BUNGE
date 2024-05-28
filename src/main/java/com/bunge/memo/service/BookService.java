package com.bunge.memo.service;

import com.bunge.memo.domain.Book;
import com.bunge.memo.filter.BookFilter;

import java.util.List;

public interface BookService {

    //책 등록
    public void addBook(Book book);

    //책 개수
    public int getBookListCount();

    //책 목록
    public List<Book> getBookList(BookFilter filter);
}
