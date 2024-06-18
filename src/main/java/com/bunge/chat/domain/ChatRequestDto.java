package com.bunge.chat.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ChatRequestDto {
    private Integer chatroomId;
    private String loginId;
    private String memberId;
    private int state;
}
