package com.bunge.memo.controller;

import com.bunge.memo.domain.Book;
import com.bunge.memo.domain.Memo;
import com.bunge.memo.domain.ReadState;
import com.bunge.memo.filter.BookFilter;
import com.bunge.memo.filter.MemoFilter;
import com.bunge.memo.service.BookService;
import com.bunge.memo.service.MemoService;
import com.bunge.memo.service.ReadStateService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value="/memo")
public class MemoController {

    private static final Logger logger = LoggerFactory.getLogger(MemoController.class);

    private final MemoService memoService;
    private final BookService bookService;
    private final ReadStateService readStateService;

    @Autowired
    public MemoController(MemoService memoService, BookService bookService, ReadStateService readStateService) {
        this.memoService = memoService;
        this.bookService = bookService;
        this.readStateService = readStateService;
    }

    @ResponseBody
    @PostMapping("/add-book")
    public ResponseEntity<String> addBook(@RequestBody Book book) {
        bookService.addBook(book);
        //logger.info(book.toString());
        return ResponseEntity.status(HttpStatus.OK).body("{\"message\":\"search-main\"}");
    }

    @GetMapping("/search-main")
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

    @ResponseBody
    @GetMapping("/search-result")
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

    @GetMapping("/bookdetail")
    public String bookDetail(String isbn13,
                             Model model) {

        BookFilter filter = new BookFilter();
        filter.setIsbn13(isbn13);
        Book book = bookService.getBookDetail(filter);

        ReadState readState = new ReadState();
        readState.setIsbn13(isbn13);
        readState.setState("도전");
        int challenge = readStateService.countReadState(readState);
        model.addAttribute("challenge", challenge);

        readState.setState("목표");
        int goal = readStateService.countReadState(readState);
        model.addAttribute("goal", goal);

        readState.setState("완독");
        int complete = readStateService.countReadState(readState);
        model.addAttribute("complete", complete);

        //logger.info("book : " + book);
        model.addAttribute("book", book);
        model.addAttribute("isbn13", isbn13);

        return "memo/book_detail";
    }

    @ResponseBody
    @PostMapping("/checkbook")
    public List<Book> checkBook(@RequestBody List<Book> books) {
        //logger.info(books.toString());
        return bookService.filterNewBooks(books);
    }

    @PostMapping("/addreadstate")
    public ResponseEntity<String> addReadState(@RequestBody ReadState readState) {

        //logger.info(readState.toString());
        bookService.updatePage(readState);

        if (readState.getState().equals("목표")) {
            try {
                readStateService.addReadState(readState);
                return ResponseEntity.status(HttpStatus.OK).body("{\"message\":\"goal success\"}");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"message\":\"goal failed\"}");
            }
        } else if (readState.getState().equals("도전")) {
            try {
                //logger.info(readState.toString());
                readStateService.addReadState(readState);
                return ResponseEntity.status(HttpStatus.OK).body("{\"message\":\"challenge success\"}");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"message\":\"challenge failed\"}");
            }
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"message\":\"state error\"}");
        }
    }

    @GetMapping("/mine")
    public ModelAndView memoMain(ModelAndView mv,
                                 @RequestParam(value="page", required=false, defaultValue="1") Integer page) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loginId = authentication.getName();
        //logger.info("loginId : " + loginId);


        List<ReadState> readStates = readStateService.getAllReadState(loginId);
        //logger.info("readStates : " + readStates.toString());

        List<Book> myGoalList = new ArrayList<>();
        List<Book> myChallengeList = new ArrayList<>();
        List<Book> myCompleteList = new ArrayList<>();

        int pageSize = 3;
        int offset = (page - 1) * pageSize;

        for (ReadState readState : readStates) {
            if (readState.getState().equals("목표")) {
                myGoalList.add(bookService.getMyBookByState(readState));
            } else if (readState.getState().equals("도전")) {
                if (readState.getTotalpage() == readState.getReadpage()) {
                    readStateService.updateReadState(readState);
                }
                myChallengeList.add(bookService.getMyBookByState(readState));
            } else if (readState.getState().equals("완독")) {
                myCompleteList.add(bookService.getMyBookByState(readState));
            }
        }

        //logger.info("myGoalList : " + myGoalList.toString());
        //logger.info(String.valueOf(myGoalList.size()));
        //logger.info("myChallengeList : " + myChallengeList.toString());
        //logger.info(String.valueOf(myChallengeList.size()));
        //logger.info("myCompleteList : " + myCompleteList);
        //logger.info(String.valueOf(myCompleteList.size()));

        MemoFilter memoFilter = new MemoFilter();
        memoFilter.setLoginId(loginId);
        memoFilter.setOffset(offset);
        memoFilter.setLimit(pageSize);

        List<Memo> myMemoList = memoService.getMyMemoList(memoFilter);
        //logger.info("myMemoList: " + myMemoList.toString());

        int totalMemos = memoService.getMemoListCount(memoFilter);
        //logger.info("totalMemos: " + totalMemos);

        int maxPage = (int) Math.ceil((double) totalMemos / pageSize);
        int startPage = Math.max(1, page - 5);
        int endPage = Math.min(maxPage, page + 5);

        mv.addObject("myGoalList", myGoalList);
        mv.addObject("myChallengeList", myChallengeList);
        mv.addObject("myCompleteList", myCompleteList);

        mv.addObject("myMemoList", myMemoList);
        mv.addObject("currentPage", page);
        mv.addObject("maxPage", maxPage);
        mv.addObject("startPage", startPage);
        mv.addObject("endPage", endPage);

        mv.setViewName("memo/memo_mine");

        return mv;
    }

    @PostMapping("/add-memo")
    public String addMemo(Memo memo) {

        //logger.info(memo.toString());
        memoService.addMemo(memo);


        return "redirect:mine";
    }


    @ResponseBody
    @PostMapping("/count-remain-page")
    public int countRemainPage(@RequestBody ReadState readState) {

        //logger.info(readState.toString());

        int countRemainPage = readStateService.countRemainPage(readState);
        return countRemainPage;
    }

    @PostMapping("/update-memo")
    public String updateMemo(@ModelAttribute Memo memo) {

        //logger.info(memo.toString());

        memoService.updateMemo(memo);

        return "redirect:mine";
    }

    @ResponseBody
    @PostMapping("/delete-memo")
    public int deleteMemo(@RequestBody Memo memo) {
        //logger.info(memo.toString());

        return memoService.deleteMemo(memo);
    }

    @ResponseBody
    @PostMapping("/change-read-state")
    public int changeReadState(@RequestBody ReadState readState) {
        int result = readStateService.changeReadState(readState);
        return result;
    }

}
