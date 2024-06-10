package com.bunge.chat.service;

import com.bunge.chat.KafkaTopic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaConsumerService {

    @KafkaListener(topics = KafkaTopic.TEMP, groupId = "kafka-consumer-group")
    public void handleMessage(String message, Acknowledgment ack) {
        log.info("consumer message={}", message);
        ack.acknowledge();
    }
}
