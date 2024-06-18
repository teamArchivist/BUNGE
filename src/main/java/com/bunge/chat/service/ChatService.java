package com.bunge.chat.service;

import com.bunge.chat.domain.ChatListDto;
import com.bunge.chat.domain.ChatRequestDto;
import com.bunge.chat.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<ChatListDto> findChatrooms(String loginMemberId) {
         return repository.findAllByMemberId(loginMemberId).stream()
                 .sorted(Comparator.comparing(ChatListDto::getCreateDate).reversed())
                 .collect(Collectors.toList());
    }
}
