package com.bunge.chat.repository;

import com.bunge.chat.domain.ChatRequestDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ChatMapper {

    void save(ChatRequestDto requestDto);

    void saveRelation(ChatRequestDto requestDto);
}
