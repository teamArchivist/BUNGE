package com.bunge.trade.controller;

import com.bunge.trade.domain.Trade;
import com.bunge.trade.service.TradeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/trade")
public class TradeController {

    @Value("${trade.my.savefolder}")
    private String saveFolder;

    private static final Logger logger = LoggerFactory.getLogger(TradeController.class);

    private final TradeService tradeService;

    @Autowired
    public TradeController(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    @GetMapping("/main")
    public ModelAndView tradeList(@RequestParam(value="page", defaultValue="1") int page,
                                  ModelAndView mv) {

        int limit = 10;
        int listCount = tradeService.getListCount();
        int maxPage = (listCount + limit - 1) / limit;
        int startPage = (page - 1) / 10 * 10 + 1;
        int endPage = startPage + 10 - 1;
        if (endPage > maxPage) {
            endPage = maxPage;
        }

        List<Trade> tradeList = tradeService.getTradeList(page, limit);

        mv.addObject("page", page);
        mv.addObject("maxPage", maxPage);
        mv.addObject("startPage", startPage);
        mv.addObject("endPage", endPage);
        mv.addObject("listCount", listCount);
        mv.addObject("tradeList", tradeList);

        mv.setViewName("trade/trade_main");
        return mv;
    }

    @GetMapping("/write")
    public String write() {
        return "trade/trade_write";
    }

    @PostMapping("/add")
    public String uploadTrade(Trade trade, HttpServletRequest request) throws IOException {
        tradeService.insertTrade(trade);
        return "redirect:/trade/main";
    }


    @GetMapping("/literature_fiction")
    public ModelAndView literatureList(@RequestParam(value="page", defaultValue="1") int page,
                                       ModelAndView mv) {

        int limit = 10;
        int listCount = tradeService.getListCount();
        int maxPage = (listCount + limit - 1) / limit;
        int startPage = (page - 1) / 10 * 10 + 1;
        int endPage = startPage + 10 - 1;
        if (endPage > maxPage) {
            endPage = maxPage;
        }

        List<Trade> tradeList = tradeService.getTradeList(page, limit);

        mv.addObject("page", page);
        mv.addObject("maxPage", maxPage);
        mv.addObject("startPage", startPage);
        mv.addObject("endPage", endPage);
        mv.addObject("listCount", listCount);
        mv.addObject("tradeList", tradeList);


        mv.setViewName("trade/trade_literature_fiction");
        return mv;
    }

    @GetMapping(value="/non-fiction")
    public ModelAndView nonfictionList(@RequestParam(value="page", defaultValue="1") int page,
                                       ModelAndView mv) {

        mv.setViewName("trade/trade_non-fiction");

        return mv;

    }

    @GetMapping(value="/children_youngadult")
    public ModelAndView childrenList(@RequestParam(value="page", defaultValue="1") int page,
                                       ModelAndView mv) {

        mv.setViewName("trade/trade_children_youngadult");

        return mv;

    }

    @GetMapping(value="/education_reference")
    public ModelAndView educationList(@RequestParam(value="page", defaultValue="1") int page,
                                       ModelAndView mv) {

        mv.setViewName("trade/trade_education_reference");

        return mv;

    }

    @GetMapping(value="/science_technology")
    public ModelAndView scienceList(@RequestParam(value="page", defaultValue="1") int page,
                                      ModelAndView mv) {

        mv.setViewName("trade/trade_science_technology");

        return mv;

    }

    @GetMapping(value="/self-development")
    public ModelAndView selfdevelopmentList(@RequestParam(value="page", defaultValue="1") int page,
                                    ModelAndView mv) {

        mv.setViewName("trade/trade_self-development");

        return mv;

    }

    @GetMapping(value="/business_economics")
    public ModelAndView businessList(@RequestParam(value="page", defaultValue="1") int page,
                                            ModelAndView mv) {

        mv.setViewName("trade/trade_business_economics");

        return mv;

    }

    @GetMapping(value="/arts_popular-culture")
    public ModelAndView artsList(@RequestParam(value="page", defaultValue="1") int page,
                                     ModelAndView mv) {

        mv.setViewName("trade/trade_arts_popular-culture");

        return mv;

    }

    @GetMapping(value="/hobbies_travel")
    public ModelAndView hobbiesList(@RequestParam(value="page", defaultValue="1") int page,
                                 ModelAndView mv) {

        mv.setViewName("trade/trade_hobbies_travel");

        return mv;

    }

    @GetMapping(value="/health_fitness")
    public ModelAndView healthList(@RequestParam(value="page", defaultValue="1") int page,
                                    ModelAndView mv) {

        mv.setViewName("trade/trade_health_fitness");

        return mv;

    }

    @GetMapping(value="/religion_spirituality")
    public ModelAndView religionList(@RequestParam(value="page", defaultValue="1") int page,
                                   ModelAndView mv) {

        mv.setViewName("trade/trade_religion_spirituality");

        return mv;

    }

    @GetMapping(value="/comics_graphic-novels")
    public ModelAndView comicsList(@RequestParam(value="page", defaultValue="1") int page,
                                     ModelAndView mv) {

        mv.setViewName("trade/trade_comics_graphic-novels");

        return mv;

    }

    @GetMapping(value="/magazines")
    public ModelAndView magazinesList(@RequestParam(value="page", defaultValue="1") int page,
                                   ModelAndView mv) {

        mv.setViewName("trade/trade_magazines");

        return mv;

    }

    @GetMapping("/detail")
    public ModelAndView detail(int tradeNo,
                               ModelAndView mv,
                               HttpServletRequest request,
                               HttpSession session,
                               @RequestHeader(value = "referer", required = false) String beforeURL) {

        /*
            1. String beforeURL = request.getHeader("referer"); 의미로
               어느 주소에서 detail로 이동했는지 header의 정보 중에서 "referer"를 통해 알 수 있습니다.
            2. 수정 후 이곳으로 이동하는 경우 조회수는 증가하지 않도록 합니다.
            3. myhome/board/list 에서 제목을 클릭한 경우 조회수가 증가하도록 합니다.
        * */

        String readCheck = (String) session.getAttribute("readCheck");
        logger.info("referer: " + beforeURL);
        if (beforeURL != null && beforeURL.endsWith("list") && readCheck != null) {
            tradeService.setReadCountUpdate(tradeNo);
            session.removeAttribute("readCheck");
        }

        Trade trade= tradeService.getDetail(tradeNo);
//        board=null; //error 페이지 이동 확인하고자 임의로 지정합니다.
        if (trade == null) {
            logger.info("상세보기 실패");
            mv.setViewName("error/error");
            mv.addObject("url", request.getRequestURL());
            mv.addObject("message", "상세보기 실패입니다.");
        } else {
            logger.info("상세보기 성공");
            int count = tradeService.getListCount();
            mv.setViewName("trade/trade_view");
            mv.addObject("count", count);
            mv.addObject("trade", trade);

        }

        return mv;
    }
}
