package com.bunge.chat.controller;

import com.bunge.chat.KafkaTopic;
import com.bunge.chat.service.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
public class ChatController {

    private final KafkaProducerService producerService;

    @GetMapping("/{data}")
    public String send(@PathVariable String data) {
        producerService.sendMessage(KafkaTopic.TEMP, data);
        return "ok";
    }
}