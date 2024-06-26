package com.bunge.chat.service;

import com.bunge.chat.util.JsonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

    private final SimpMessagingTemplate template;
    private final JsonUtils jsonUtils;

    public void handleMessage(String message, Acknowledgment ack) throws JsonProcessingException {
        log.info("consumer message={}", message);
        template.convertAndSend("/rooms/" + jsonUtils.getObject(message).getChatroomId(), message);
        ack.acknowledge();
    }
}
