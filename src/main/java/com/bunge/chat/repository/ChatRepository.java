package com.bunge.chat.repository;

import com.bunge.chat.domain.ChatListDto;
import com.bunge.chat.domain.ChatRequestDto;

import java.util.List;

public interface ChatRepository {

    ChatRequestDto save(ChatRequestDto requestDto);

    void saveRelation(ChatRequestDto requestDto);

    Chatroom findById(Integer id);

    List<ChatListDto> findAllByMemberId(String memberId);
}
