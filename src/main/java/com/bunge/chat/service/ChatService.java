package com.bunge.chat.service;


import com.bunge.chat.domain.ChatRequestDto;
import com.bunge.chat.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository repository;

    @Transactional
    public ChatRequestDto createChatroom(ChatRequestDto requestDto) {
        ChatRequestDto save = repository.save(requestDto);
        repository.saveRelation(requestDto);
        return save;
    }
}
