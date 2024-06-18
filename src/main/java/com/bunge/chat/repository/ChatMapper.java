package com.bunge.chat.repository;

import com.bunge.chat.domain.ChatListDto;
import com.bunge.chat.domain.ChatRequestDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChatMapper {

    void save(ChatRequestDto requestDto);

    void saveRelation(ChatRequestDto requestDto);

    List<ChatListDto> findAllByMemberId(String memberId);
}
