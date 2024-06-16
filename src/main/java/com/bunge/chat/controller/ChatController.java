package com.bunge.chat.controller;

import com.bunge.chat.domain.Chatroom;
import com.bunge.chat.service.ChatService;
import com.bunge.member.domain.Member;
import com.bunge.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {

    private final MemberService memberService;

    @GetMapping
    public String showChatMainPage() {
        return "chat/chat";
    }

    @GetMapping("/rooms")
    public String showCreatePage(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "chat/chat-enter";
    }
}