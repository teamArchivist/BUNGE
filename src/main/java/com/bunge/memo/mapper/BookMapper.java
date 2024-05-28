package com.bunge.memo.mapper;

import com.bunge.memo.domain.Book;
import com.bunge.memo.filter.BookFilter;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BookMapper {

    //책 등록
    public void addBook(Book book);

    //책 목록
    public List<Book> getBookList(BookFilter filter);

    //책 개수
    public int getBookListCount(BookFilter filter);
}
