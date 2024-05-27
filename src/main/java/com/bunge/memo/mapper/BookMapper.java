package com.bunge.memo.mapper;

import com.bunge.memo.domain.Book;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BookMapper {

    //책 등록
    public void addBook(Book book);
}
