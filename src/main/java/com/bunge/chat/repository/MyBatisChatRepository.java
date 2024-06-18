package com.bunge.chat.repository;

import com.bunge.chat.domain.ChatListDto;
import com.bunge.chat.domain.ChatRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
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

    @Override
    public List<ChatListDto> findAllByMemberId(String loginMemberId) {
        return chatMapper.findAllByMemberId(loginMemberId);
    }

}
