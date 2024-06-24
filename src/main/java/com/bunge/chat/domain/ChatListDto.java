package com.bunge.chat.domain;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class ChatListDto {
    private Integer chatroomId;
    private String nick;
    private String profile;
    private String state;
    private String type;
    private String data;
    private LocalDateTime createDate;
}
