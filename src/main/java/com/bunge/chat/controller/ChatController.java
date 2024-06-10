package com.bunge.chat.controller;

import com.bunge.chat.service.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/chat")
public class ChatController {

    private final KafkaProducerService producerService;

    @GetMapping
    public String chatPage() {
        return "chat/chat";
    }
}