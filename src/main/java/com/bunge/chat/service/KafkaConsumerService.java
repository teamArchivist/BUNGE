package com.bunge.chat.service;

import com.bunge.chat.KafkaTopic;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

    private final SimpMessagingTemplate template;

    public void handleMessage(String topic, String message, Acknowledgment ack) {
        log.info("consumer message={}", message);
        template.convertAndSend("/" + topic, message);
        ack.acknowledge();
    }
}
