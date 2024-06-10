package com.bunge.trade.controller;

import com.bunge.trade.domain.Report;
import com.bunge.trade.domain.Trade;
import com.bunge.trade.domain.TradeImage;
import com.bunge.trade.service.TradeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

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

        // Trade에 대한 이미지 리스트 가져오기

        mv.addObject("page", page);
        mv.addObject("maxPage", maxPage);
        mv.addObject("startPage", startPage);
        mv.addObject("endPage", endPage);
        mv.addObject("listCount", listCount);
        mv.addObject("tradeList", tradeList);

        mv.setViewName("trade/trade_main");
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

        Trade trade = tradeService.getDetail(tradeNo);
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

            // Trade에 대한 이미지 리스트 가져오기
            List<TradeImage> tradeImages = tradeService.getTradeImages(tradeNo);

            // TradeImage에서 imagePath를 가져와서 모델에 추가
            List<String> imagePaths = new ArrayList<>();
            for (TradeImage image : tradeImages) {
                imagePaths.add(image.getImagePath());
            }
            mv.addObject("tradeImages", imagePaths);

        }

        return mv;
    }


//    @GetMapping("/main")
//    public ModelAndView tradeList(@RequestParam(value="page", defaultValue="1") int page, int tradeNo,
//                                  ModelAndView mv) {
//
//        int limit = 10;
//        int listCount = tradeService.getListCount();
//        int maxPage = (listCount + limit - 1) / limit;
//        int startPage = (page - 1) / 10 * 10 + 1;
//        int endPage = startPage + 10 - 1;
//        if (endPage > maxPage) {
//            endPage = maxPage;
//        }
//
//        List<Trade> tradeList = tradeService.getTradeList(page, limit);
//
//        mv.addObject("page", page);
//        mv.addObject("maxPage", maxPage);
//        mv.addObject("startPage", startPage);
//        mv.addObject("endPage", endPage);
//        mv.addObject("listCount", listCount);
//        mv.addObject("tradeList", tradeList);
//
//
//        // Trade에 대한 이미지 리스트 가져오기
//        List<TradeImage> tradeImages = tradeService.getTradeImages(tradeNo);
//
//        // TradeImage에서 imagePath를 가져와서 모델에 추가
//        List<String> imagePaths = new ArrayList<>();
//        for (TradeImage image : tradeImages) {
//            imagePaths.add(image.getImagePath());
//        }
//        mv.addObject("tradeImages", imagePaths);
//
//        mv.setViewName("trade/trade_main");
//        return mv;
//    }

    @GetMapping("/write")
    public String write() {
        return "trade/trade_write";
    }

    @PostMapping("/add")
    public String uploadTrade(@RequestParam("file") MultipartFile file, Trade trade, HttpServletRequest request) throws IOException {
        // 파일 경로 설정
        String uploadDir = "./src/main/resources/static/img/trade/testsave/";

        // 파일 저장
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir + fileName);
        Files.copy(file.getInputStream(), filePath);

        // Trade 데이터베이스에 삽입
        tradeService.insertTrade(trade);

        // Trade 객체에서 자동 생성된 tradeno 값을 가져옴
        int tradeNo = trade.getTradeNo();

        // TradeImage 객체 생성 및 데이터베이스에 삽입
        TradeImage tradeImage = new TradeImage();
        tradeImage.setTradeNo(tradeNo); // Trade 번호 설정
        tradeImage.setImagePath("/img/trade/testsave/" + fileName); // 이미지 경로 설정
        tradeService.insertTradeImage(tradeImage);

        return "redirect:/trade/main";
    }


    @GetMapping("/literature_fiction")
    public ModelAndView literatureList(@RequestParam(value = "page", defaultValue = "1") int page) {
        String categoryID = "문학/소설";
        List<Trade> trades = tradeService.getTradesByCategory(categoryID);

        ModelAndView mv = new ModelAndView();
        mv.addObject("trades", trades);
        mv.setViewName("trade/trade_literature_fiction"); // view name 설정

        return mv;
    }

    @GetMapping(value="/non-fiction")
    public ModelAndView nonfictionList(@RequestParam(value="page", defaultValue="1") int page) {
        String categoryID = "논픽션";
        List<Trade> trades = tradeService.getTradesByCategory(categoryID);

        ModelAndView mv = new ModelAndView();
        mv.addObject("trades", trades);
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



    @GetMapping("/update/{tradeNo}")
    public ModelAndView updateForm(@PathVariable("tradeNo") int tradeNo, ModelAndView mv) {
        Trade trade = tradeService.getTrade(tradeNo);
        mv.addObject("trade", trade);
        mv.setViewName("trade/trade_update"); // 수정 폼으로 이동
        return mv;
    }

    @PostMapping("/update")
    public String updateTrade(@ModelAttribute Trade trade) {
        tradeService.updateTrade(trade); // 상품 수정
        return "redirect:/trade/detail?tradeNo=" + trade.getTradeNo(); // 수정된 상품의 상세 페이지로 이동
    }

    // 신고 처리
    @PostMapping("/report")
    @ResponseBody
    public String submitReport(@RequestParam("tradeNo") int tradeNo,
                               @RequestParam("reason") String reason,
                               @RequestParam(value = "details", required = false) String details) {
        Report report = new Report();
        report.setTradeNo(tradeNo);
        report.setReason(reason);
        report.setDetails(details);

        tradeService.submitReport(report);
        return "신고가 접수되었습니다.";
    }

    @PostMapping("/delete/{tradeNo}")
    public String deleteTrade(@PathVariable int tradeNo) {
        tradeService.deleteTrade(tradeNo);
        return "redirect:/trade/main"; // 수정된 부분
    }



}
