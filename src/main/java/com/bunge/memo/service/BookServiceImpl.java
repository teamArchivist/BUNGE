package com.bunge.memo.service;

import com.bunge.memo.domain.Book;
import com.bunge.memo.mapper.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

    private BookMapper bookMapper;

    @Autowired
    public BookServiceImpl(BookMapper bookMapper) { this.bookMapper = bookMapper; }

    @Override
    public void addBook(Book book) {
        bookMapper.addBook(book);
    }
}
