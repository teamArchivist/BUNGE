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

import java.util.List;

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
        logger.info(memo.toString());
        return "redirect:mine";
    }

    @GetMapping("/search")
    public String searchBooks(@RequestParam(value = "title", required = false) String title,
                              @RequestParam(value = "author", required = false) String author,
                              @RequestParam(value = "category", required = false) String category,
                              @RequestParam(value = "score", required = false, defaultValue = "0") Integer score,
                              @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                              @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                              Model model) {
        BookFilter filter = new BookFilter();
        filter.setTitle(title);
        filter.setAuthor(author);
        filter.setCategory(category);
        filter.setScore(score);
        filter.setPage(page);
        filter.setPageSize(pageSize);

        List<Book> books = bookService.getBookList(filter);
        int listcount = bookService.getBookListCount();
        model.addAttribute("books", books);
        model.addAttribute("listcount", listcount);
        return "memo/book_search";
    }

    @ResponseBody
    @PostMapping("/search_result")
    public List<Book> getBooks(@RequestParam(value = "page", required = false) Integer page,
                               @RequestParam(value = "pageSize", required = false) Integer pageSize,
                               @RequestParam(value = "title", required = false) String title,
                               @RequestParam(value = "author", required = false) String author,
                               @RequestParam(value = "category", required = false) String category,
                               @RequestParam(value = "score", required = false, defaultValue = "0") Integer score) {
        BookFilter filter = new BookFilter();
        filter.setPage(page);
        filter.setPageSize(pageSize);
        filter.setTitle(title);
        filter.setAuthor(author);
        filter.setCategory(category);
        filter.setScore(score);
        return bookService.getBookList(filter);
    }

    @ResponseBody
    @PostMapping("/addbook")
    public ResponseEntity<String> addBook(@RequestBody Book book) {
        bookService.addBook(book);
        logger.info(book.toString());
        return ResponseEntity.status(HttpStatus.OK).body("{\"message\":\"search\"}");
    }
}
