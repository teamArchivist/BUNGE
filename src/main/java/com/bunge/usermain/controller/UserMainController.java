package com.bunge.usermain.controller;

import com.bunge.study.domain.StudyManagement;
import com.bunge.usermain.service.UserMainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private UserMainService userMainService;

    @GetMapping("/main")
    public String userMianView(Model model,Principal principal) {

        String loginId = principal.getName();

        model.addAttribute("loginId",loginId);

        System.out.println("여기는 main");
        return "user_main";
    }

    @ResponseBody
    @GetMapping("/user-info")
        public Map<String, Object> userMainInfo(Principal principal,
                                                @RequestParam(defaultValue = "1") int page,
                                                @RequestParam(defaultValue = "5") int size,
                                                @RequestParam(defaultValue = "name") String sort) {

            String memberId = principal.getName();  // 현재 로그인된 사용자의 아이디

            Map<String, Object> response = new HashMap<>();
            int offset = (page - 1) * size;

        try {
            List<StudyManagement> studyMyList = userMainService.selectStudyBoardByMemberId(memberId, size, offset, sort);
            System.out.println("studyMyList:" + studyMyList);

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

}
