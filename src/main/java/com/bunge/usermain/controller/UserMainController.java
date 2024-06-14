package com.bunge.usermain.controller;

import com.bunge.usermain.service.UserMainService;
import com.bunge.study.domain.StudyBoard;
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
    private UserMainService memberMainService;

  //  @RequestMapping("/main")
   // public String showMainPage() {

     //   return "member_main";
  //  }

    @GetMapping("/main")
    public String mianPageView(Model model) {

        return "user_main";

    }

    @ResponseBody
    @PostMapping("/user-info")
        public Map<String, Object> memberMainPost(Principal principal) {

            String loginId = principal.getName();  // 현재 로그인된 사용자의 아이디

            Map<String, Object> response = new HashMap<>();
        try {

            List<StudyBoard> studyBoards = memberMainService.selectStudyboardByMemberId(loginId);
            int studyBoardCount = memberMainService.countStudyboardByMemberId(loginId);

            response.put("status", "success");
            response.put("message", "댓글 추가 성공");
        } catch (Exception e) {

            response.put("status", "error");
            response.put("message", "목록을 불러오는데 실패했습니다.");
        }

        return response;
    }

}
