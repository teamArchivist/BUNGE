package com.bunge.chat.controller;

import com.bunge.chat.domain.ChatRequestDto;
import com.bunge.chat.service.ChatService;
import com.bunge.member.domain.Member;
import com.bunge.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {

    private final ChatService service;
    private final MemberService memberService;

    @GetMapping
    public String showChatMainPage() {
        return "chat/chat";
    }

    @PostMapping("/rooms")
    public String createChat(@AuthenticationPrincipal Member loginMember,
                             @ModelAttribute Member member) {
        // TODO: 사용자 검증 로직 구현
        ChatRequestDto requestDto = ChatRequestDto.builder()
                .loginId(loginMember.getId())
                .memberId(member.getId())
                .state(1)
                .build();
        ChatRequestDto chatroom = service.createChatroom(requestDto);
        return "redirect:/chat/rooms/" + chatroom.getChatroomId();
    }

    @GetMapping("/rooms")
    public String showCreatePage(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "chat/chat-enter";
    }
}