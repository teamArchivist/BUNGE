package com.bunge.memo.controller;

import com.bunge.memo.domain.Book;
import com.bunge.memo.domain.Memo;
import com.bunge.memo.filter.BookFilter;
import com.bunge.memo.service.BookService;
import com.bunge.memo.service.MemoService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value="/memo")
public class MemoController {

    private static final Logger logger = LoggerFactory.getLogger(MemoController.class);

    private final MemoService memoService;
    private final BookService bookService;

    @Autowired
    public MemoController(MemoService memoService, BookService bookService) {
        this.memoService = memoService;
        this.bookService = bookService;
    }

    //기록·리뷰 -> 나의 기록 눌렀을 때 처음 페이지
    @GetMapping("/mine")
    public ModelAndView memoMain(@RequestParam(value="page", defaultValue="1") int page, ModelAndView mv) {
        mv.setViewName("memo/memo_mine");
        return mv;
    }

    @PostMapping("/addmemo")
    public String addMemo(Memo memo, HttpServletRequest request) {
        memoService.addMemo(memo);
        return "redirect:mine";
    }

    @ResponseBody
    @PostMapping("/addbook")
    public ResponseEntity<String> addBook(@RequestBody Book book) {
        bookService.addBook(book);
        logger.info(book.toString());
        return ResponseEntity.status(HttpStatus.OK).body("{\"message\":\"searchmain\"}");
    }

    @GetMapping("/searchmain")
    public String SearchBooks(@RequestParam(value = "title", required = false) String title,
                              @RequestParam(value = "author", required = false) String author,
                              @RequestParam(value = "category", required = false) String category,
                              @RequestParam(value = "score", required = false) Integer score,
                              @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                              Model model) {

        int pageSize = 12;
        int offset = (page - 1) * pageSize;

        BookFilter filter = new BookFilter();
        filter.setTitle(title);
        filter.setAuthor(author);
        filter.setCategory(category);
        filter.setScore(score);
        filter.setOffset(offset);
        filter.setLimit(pageSize);

        List<Book> books = bookService.getBookList(filter);
        //logger.info("books : " + books.toString());

        int totalBooks = bookService.getBookListCount(filter);

        int maxPage = (int) Math.ceil((double) totalBooks / pageSize);

        int startPage = Math.max(1, page - 5);
        int endPage = Math.min(maxPage, page + 4);

        model.addAttribute("books", books);
        model.addAttribute("currentPage", page);
        model.addAttribute("maxPage", maxPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "memo/book_search";
    }

    @GetMapping("/searchresult")
    @ResponseBody
    public Map<String, Object> searchBooks(@RequestParam(value = "title", required = false) String title,
                                           @RequestParam(value = "author", required = false) String author,
                                           @RequestParam(value = "category", required = false) String category,
                                           @RequestParam(value = "score", required = false) Integer score,
                                           @RequestParam(value = "page", required = false) Integer page) {

        int pageSize = 12;
        int offset = (page - 1) * pageSize;

        BookFilter filter = new BookFilter();
        filter.setTitle(title);
        filter.setAuthor(author);
        filter.setCategory(category);
        filter.setScore(score);
        filter.setOffset(offset);
        filter.setLimit(pageSize);

        //logger.info("offset : " + offset);

        List<Book> books = bookService.getBookList(filter);
        //logger.info("filter : " + filter.toString());
        //logger.info("books : " + books.toString());

        int totalBooks = bookService.getBookListCount(filter);

        int maxPage = (int) Math.ceil((double) totalBooks / pageSize);
        int startPage = Math.max(1, page - 5);
        int endPage = Math.min(maxPage, page + 4);

        Map<String, Object> response = new HashMap<>();
        response.put("books", books);
        response.put("currentPage", page);
        response.put("maxPage", maxPage);
        response.put("startPage", startPage);
        response.put("endPage", endPage);

        return response;

    }




}
