package com.bunge.chat.domain;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    private String type;
    @Setter
    private String sender;
    private String receiver;
    private Object data;

    public void newConnect() {
        this.type = "new";
    }

    public void closeConnect() {
        this.type = "close";
    }
}
