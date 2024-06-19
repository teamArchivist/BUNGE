package com.bunge.chat.controller;

import com.bunge.chat.constant.KafkaTopic;
import com.bunge.chat.service.KafkaConsumerService;
import com.bunge.chat.service.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class MessageController {

    private final KafkaProducerService producerService;
    private final KafkaConsumerService consumerService;

    @MessageMapping("/hello")
    public void publishMessage(String message) {
        producerService.sendMessage(KafkaTopic.TEMP, message);
    }

    @KafkaListener(topics = KafkaTopic.TEMP, groupId = "kafka-consumer-group")
    public void consumeMessage(String message, Acknowledgment ack) {
        consumerService.handleMessage(KafkaTopic.TEMP, message, ack);
    }
}
