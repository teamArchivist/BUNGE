package com.bunge.memo.service;

import com.bunge.memo.domain.Book;
import com.bunge.memo.mapper.BookMapper;
import com.bunge.memo.filter.BookFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private BookMapper bookMapper;

    @Autowired
    public BookServiceImpl(BookMapper bookMapper) { this.bookMapper = bookMapper; }

    @Override
    public void addBook(Book book) {
        bookMapper.addBook(book);
    }

    @Override
    public int getBookListCount(BookFilter filter) {
        return bookMapper.getBookListCount(filter);
    }

    @Override
    public List<Book> getBookList(BookFilter filter) {
        return bookMapper.getBookList(filter);
    }
}
