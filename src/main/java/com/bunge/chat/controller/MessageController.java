package com.bunge.chat.controller;

import com.bunge.chat.domain.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class MessageController {

    @MessageMapping("/hello")
    @SendTo("/sub/msg")
    public String message(String message) {
        return message;
    }
}
