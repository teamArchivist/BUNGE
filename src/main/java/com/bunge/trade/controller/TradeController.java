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

    @RequestMapping(value="/write")
    public ModelAndView tradeWrite(@RequestParam(value="page", defaultValue="1") int page,
                                   ModelAndView mv) {
        mv.setViewName("trade/trade_write");

        return mv;
    }

    @RequestMapping(value="/literature_fiction")
    public ModelAndView literatureList(@RequestParam(value="page", defaultValue="1") int page,
                                  ModelAndView mv) {

        mv.setViewName("trade/trade_literature_fiction");

        return mv;

    }

    @RequestMapping(value="/non-fiction")
    public ModelAndView nonfictionList(@RequestParam(value="page", defaultValue="1") int page,
                                       ModelAndView mv) {

        mv.setViewName("trade/trade_non-fiction");

        return mv;

    }

    @RequestMapping(value="/children_youngadult")
    public ModelAndView childrenList(@RequestParam(value="page", defaultValue="1") int page,
                                       ModelAndView mv) {

        mv.setViewName("trade/trade_children_youngadult");

        return mv;

    }

    @RequestMapping(value="/education_reference")
    public ModelAndView educationList(@RequestParam(value="page", defaultValue="1") int page,
                                       ModelAndView mv) {

        mv.setViewName("trade/trade_education_reference");

        return mv;

    }

    @RequestMapping(value="/science_technology")
    public ModelAndView scienceList(@RequestParam(value="page", defaultValue="1") int page,
                                      ModelAndView mv) {

        mv.setViewName("trade/trade_science_technology");

        return mv;

    }

    @RequestMapping(value="/self-development")
    public ModelAndView selfdevelopmentList(@RequestParam(value="page", defaultValue="1") int page,
                                    ModelAndView mv) {

        mv.setViewName("trade/trade_self-development");

        return mv;

    }

    @RequestMapping(value="/business_economics")
    public ModelAndView businessList(@RequestParam(value="page", defaultValue="1") int page,
                                            ModelAndView mv) {

        mv.setViewName("trade/trade_business_economics");

        return mv;

    }

    @RequestMapping(value="/arts_popular-culture")
    public ModelAndView artsList(@RequestParam(value="page", defaultValue="1") int page,
                                     ModelAndView mv) {

        mv.setViewName("trade/trade_arts_popular-culture");

        return mv;

    }

    @RequestMapping(value="/hobbies_travel")
    public ModelAndView hobbiesList(@RequestParam(value="page", defaultValue="1") int page,
                                 ModelAndView mv) {

        mv.setViewName("trade/trade_hobbies_travel");

        return mv;

    }

    @RequestMapping(value="/health_fitness")
    public ModelAndView healthList(@RequestParam(value="page", defaultValue="1") int page,
                                    ModelAndView mv) {

        mv.setViewName("trade/trade_health_fitness");

        return mv;

    }

    @RequestMapping(value="/religion_spirituality")
    public ModelAndView religionList(@RequestParam(value="page", defaultValue="1") int page,
                                   ModelAndView mv) {

        mv.setViewName("trade/trade_religion_spirituality");

        return mv;

    }

    @RequestMapping(value="/comics_graphic-novels")
    public ModelAndView comicsList(@RequestParam(value="page", defaultValue="1") int page,
                                     ModelAndView mv) {

        mv.setViewName("trade/trade_comics_graphic-novels");

        return mv;

    }

    @RequestMapping(value="/magazines")
    public ModelAndView magazinesList(@RequestParam(value="page", defaultValue="1") int page,
                                   ModelAndView mv) {

        mv.setViewName("trade/trade_magazines");

        return mv;

    }



    @RequestMapping(value="/detail")
    public ModelAndView tradeDetail(@RequestParam(value="page", defaultValue="1") int page,
                                  ModelAndView mv) {

        mv.setViewName("trade/trade_detail");

        return mv;

    }
}
