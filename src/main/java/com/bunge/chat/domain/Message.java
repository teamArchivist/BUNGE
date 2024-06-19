package com.bunge.chat.domain;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Message {
    private int id;
    private int chatroomId;
    private String memberId;
    private String type;
    private String data;
    private LocalDateTime createDate;
}
