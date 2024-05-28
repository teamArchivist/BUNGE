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

    @GetMapping("/search")
    public String searchBooks(@RequestParam(value = "title", required = false) String title,
                              @RequestParam(value = "author", required = false) String author,
                              @RequestParam(value = "category", required = false) String category,
                              @RequestParam(value = "score", required = false) Integer score,
                              @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                              @RequestParam(value = "pageSize", required = false, defaultValue = "12") Integer pageSize,
                              Model model) {

        BookFilter filter = new BookFilter();
        filter.setTitle(title);
        filter.setAuthor(author);
        filter.setCategory(category);
        filter.setScore(score);
        filter.setPage((page - 1) * pageSize);
        filter.setPageSize(pageSize);

        List<Book> books = bookService.getBookList(filter);
        int totalBooks = bookService.getBookListCount();
        int maxPage = (int) Math.ceil((double) totalBooks / pageSize);

        model.addAttribute("books", books);
        model.addAttribute("currentPage", page);
        model.addAttribute("maxPage", maxPage);

        return "memo/book_search";
    }

    @ResponseBody
    @PostMapping("/search_result")
    public Map<String, Object> getBooks(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                        @RequestParam(value = "title", required = false) String title,
                                        @RequestParam(value = "author", required = false) String author,
                                        @RequestParam(value = "category", required = false) String category,
                                        @RequestParam(value = "score", defaultValue = "0") Integer score) {

        BookFilter filter = new BookFilter();

        int limit = 10;
        int listcount = bookService.getBookListCount();
        int maxpage = (listcount + limit - 1) / limit;
        int startpage = ((page - 1) / 10) * 10 + 1;
        int endpage = startpage + 10 - 1;

        if (endpage > maxpage)
            endpage = maxpage;

        int offset = (page - 1) * limit;
        filter.setPage(offset);
        filter.setPageSize(limit);
        filter.setTitle(title);
        filter.setAuthor(author);
        filter.setCategory(category);
        filter.setScore(score);

        List<Book> books = bookService.getBookList(filter);

        Map<String, Object> result = new HashMap<>();
        result.put("books", books);
        result.put("currentPage", page);
        result.put("maxPage", maxpage);
        result.put("startPage", startpage);
        result.put("endPage", endpage);
        result.put("totalBooks", listcount);

        return result;

    }

    @ResponseBody
    @PostMapping("/addbook")
    public ResponseEntity<String> addBook(@RequestBody Book book) {
        bookService.addBook(book);
        logger.info(book.toString());
        return ResponseEntity.status(HttpStatus.OK).body("{\"message\":\"search\"}");
    }
}
