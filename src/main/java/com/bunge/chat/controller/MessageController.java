package com.bunge.chat.controller;

import com.bunge.chat.constant.KafkaTopic;
import com.bunge.chat.domain.Message;
import com.bunge.chat.service.KafkaConsumerService;
import com.bunge.chat.service.KafkaProducerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MessageController {

    private final KafkaProducerService producerService;
    private final KafkaConsumerService consumerService;

    @MessageMapping("/message")
    public void publishMessage(Message message) throws JsonProcessingException {
        log.info("message={}", message);
        producerService.sendMessage(KafkaTopic.TEMP, message);
    }

    @KafkaListener(topics = KafkaTopic.TEMP, groupId = "kafka-consumer-group3")
    public void consumeMessage(String message, Acknowledgment ack) {
        consumerService.handleMessage(KafkaTopic.TEMP, message, ack);
    }
}
