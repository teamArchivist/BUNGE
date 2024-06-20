package com.bunge.chat.service;

import com.bunge.chat.repository.ChatRepository;
import com.bunge.chat.util.JsonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ChatRepository repository;

    public void sendMessage(String topic, String data) throws JsonProcessingException {
        log.info("producer message={}", data);
        kafkaTemplate.send(topic, data);
        repository.saveMessage(JsonUtils.getObject(data));
    }
}





