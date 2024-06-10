package com.bunge.chat.controller;

import com.bunge.chat.KafkaTopic;
import com.bunge.chat.domain.Message;
import com.bunge.chat.service.KafkaConsumerService;
import com.bunge.chat.service.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
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
