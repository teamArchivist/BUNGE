package com.bunge.chat.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class ChatMessageDto {
    private Integer chatroomId;
    private String loginId;
    private List<Message> messages;
}
