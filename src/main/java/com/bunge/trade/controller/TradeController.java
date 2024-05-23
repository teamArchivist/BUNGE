package com.bunge.trade.controller;


import com.bunge.review.controller.ReviewController;
import com.bunge.review.service.ReviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/trade")
public class TradeController {

    private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);

    private ReviewService reviewservice;

    @Autowired
    public TradeController(ReviewService reviewservice) {
        this.reviewservice = reviewservice;
    }

    @RequestMapping(value="/main")
    public ModelAndView tradeList(@RequestParam(value="page", defaultValue="1") int page,
                                   ModelAndView mv) {

        mv.setViewName("trade/trade_main");

        return mv;

    }

    @RequestMapping(value="/detail")
    public ModelAndView tradeDetail(@RequestParam(value="page", defaultValue="1") int page,
                                  ModelAndView mv) {

        mv.setViewName("trade/trade_detail");

        return mv;

    }
}
