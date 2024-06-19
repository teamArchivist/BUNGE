package com.bunge.chat.controller;

import com.bunge.chat.domain.ChatListDto;
import com.bunge.chat.domain.ChatMessageDto;
import com.bunge.chat.domain.ChatRequestDto;
import com.bunge.chat.service.ChatService;
import com.bunge.member.domain.Member;
import com.bunge.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {

    private final ChatService service;
    private final MemberService memberService;

    @GetMapping
    public String getChatList(@AuthenticationPrincipal Member loginMember,
                              Model model) {
        // TODO:
        //  - 채팅 페이지 클릭 후 로그인 시 바로 채팅 페이지로 Redirect
        List<ChatListDto> chatrooms = service.findChatrooms(loginMember.getId());
        model.addAttribute("chatrooms", chatrooms);
        return "chat/chat";
    }

    @GetMapping("/rooms/{id}")
    public String getChatDetail(@PathVariable Integer id,
                                @AuthenticationPrincipal Member loginMember,
                                Model model) {
        // TODO: 한 채팅룸의 메세지 데이터와 다른 채팅방 리스트 + 최근 메세지 반환
        List<ChatListDto> chatrooms = service.findChatrooms(loginMember.getId());
        model.addAttribute("chatrooms", chatrooms);
        model.addAttribute("id", id);
        ChatMessageDto messageDto = service.findChatroom(id);
        messageDto.setLoginId(loginMember.getId());
        messageDto.setChatroomId(id);
        model.addAttribute("messageDto", messageDto);
        return "chat/chat";
    }

    @PostMapping("/rooms")
    public String createChat(@AuthenticationPrincipal Member loginMember,
                             @ModelAttribute Member member) {
        // TODO: 사용자 검증 로직 구현
        //  - state 제거
        ChatRequestDto requestDto = ChatRequestDto.builder()
                .loginId(loginMember.getId())
                .memberId(member.getId())
                .state(1)
                .build();
        ChatRequestDto chatroom = service.createChatroom(requestDto);
        return "redirect:/chat/rooms/" + chatroom.getChatroomId();
    }

    @GetMapping("/rooms")
    public String showCreatePage(Model model,
                                 @AuthenticationPrincipal Member loginMember) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        model.addAttribute("loginId", loginMember.getId());
        return "chat/chat-enter";
    }
}