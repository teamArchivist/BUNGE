package com.bunge.chat.repository;

import com.bunge.chat.domain.ChatRequestDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MyBatisChatRepository implements ChatRepository{

    private final ChatMapper chatMapper;


    @Override
    public ChatRequestDto save(ChatRequestDto requestDto) {
        chatMapper.save(requestDto);
        return requestDto;
    }

    @Override
    public ChatRequestDto saveRelation(ChatRequestDto requestDto) {
        chatMapper.saveRelation(requestDto);
        return requestDto;
    }
}
