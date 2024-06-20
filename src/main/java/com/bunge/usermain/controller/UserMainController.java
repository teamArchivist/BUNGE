package com.bunge.usermain.controller;

import com.bunge.study.domain.Notice;
import com.bunge.study.domain.StudyEvent;
import com.bunge.study.domain.StudyManagement;
import com.bunge.study.service.NoticeService;
import com.bunge.usermain.service.UserMainService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/user")
public class UserMainController {

    private final UserMainService userMainService;

    public UserMainController(UserMainService userMainService) {
        this.userMainService = userMainService;
    }

    @GetMapping("/main")
    public String userMainView(Model model,Principal principal , HttpSession session) {
        String memberId = principal.getName();

        List<Notice> studies = userMainService.selectStudiesByMemberId(memberId);
        model.addAttribute("memberId", memberId);
        model.addAttribute("studies", studies);

        Map<Integer, List<Notice>> studyNotices = new HashMap<>();
        for (Notice study : studies) {
            List<Notice> notices = userMainService.selectNoticesByStudyBoardNo(study.getStudyboardno(), 1, 5);
            studyNotices.put(study.getStudyboardno(), notices);
        }
        model.addAttribute("studyNotices", studyNotices);

        if (studies.isEmpty()) {
            model.addAttribute("noStudiesMessage", "가입되어 있는 스터디가 없습니다.");
        }

        model.addAttribute("message", session.getAttribute("message"));
        session.removeAttribute("message");
        return "user_main";
    }

    @ResponseBody
    @GetMapping("/user-studyList")
        public Map<String, Object> userStudyList(Principal principal,
                                                @RequestParam(defaultValue = "1") int page,
                                                @RequestParam(defaultValue = "5") int size,
                                                @RequestParam(defaultValue = "studystart") String sort) {

            String memberId = principal.getName();  // 현재 로그인된 사용자의 아이디

            Map<String, Object> response = new HashMap<>();

            int offset = (page - 1) * size;

        try {
            List<StudyManagement> studyMyList = userMainService.selectStudyBoardByMemberId(memberId, size, offset, sort);
//            System.out.println("studyMyList:" + studyMyList);

            int studyListCount = userMainService.countStudyBoardByMemberId(memberId);

            response.put("studyMyList", studyMyList);
            response.put("studyListCount", studyListCount);
            response.put("status", "success");
        } catch (Exception e) {
            e.printStackTrace(); // 예외 스택 트레이스 출력
            response.put("status", "error");
            response.put("message", "목록을 불러오는데 실패했습니다.");
        }
        return response;
    }

    @ResponseBody
    @GetMapping("/user-eventList")
    public Map<String, Object> userEventList(Principal principal,
                                             @RequestParam(defaultValue = "1") int page,
                                             @RequestParam(defaultValue = "5") int size) {


        String memberId = principal.getName();  // 현재 로그인된 사용자의 아이디
//        log.info("memberId: {}", memberId);

        Map<String, Object> response = new HashMap<>();
        int offset = (page - 1) * size;

        try {
//            log.info("Calling selectMyEvent with memberId: {}, size: {}, offset: {}", memberId, size, offset);

            List<StudyEvent> studyEvent = userMainService.selectMyEvent(memberId, size, offset);
            int eventCount = userMainService.countMyEvent(memberId);

//            log.info("studyEvent: {}", studyEvent); // 디버깅 출력
//            log.info("eventCount: {}", eventCount); // 디버깅 출력

            // 데이터를 response 맵에 추가
            response.put("studyEvent", studyEvent);
            response.put("eventCount", eventCount);
            response.put("status", "success");
//            log.info("response: {}", response); // 디버깅 출력
        } catch (Exception e) {
//            log.error("Error fetching user event list", e);
            response.put("status", "error");
            response.put("message", "목록을 불러오는데 실패했습니다.");
        }
        return response;
    }

}
