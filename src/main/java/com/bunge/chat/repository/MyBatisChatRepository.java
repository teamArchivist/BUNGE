package com.bunge.chat.repository;

import com.bunge.chat.domain.ChatListDto;
import com.bunge.chat.domain.ChatRequestDto;
import com.bunge.chat.domain.Message;
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
    public void saveRelation(ChatRequestDto requestDto) {
        chatMapper.saveRelation(requestDto);
    }

    @Override
    public List<Message> findById(Integer id) {
        return chatMapper.findById(id);
    }

    @Override
    public List<ChatListDto> findAllByMemberId(String loginMemberId) {
        return chatMapper.findAllByMemberId(loginMemberId);
    }

    @Override
    public void saveMessage(Message message) {
        chatMapper.saveMessage(message);
    }

}
