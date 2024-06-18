package com.bunge.chat.repository;

import com.bunge.chat.domain.ChatRequestDto;

public interface ChatRepository {

    ChatRequestDto save(ChatRequestDto requestDto);

    ChatRequestDto saveRelation(ChatRequestDto requestDto);
}
