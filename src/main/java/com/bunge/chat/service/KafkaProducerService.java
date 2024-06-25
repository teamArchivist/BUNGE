package com.bunge.chat.service;

import com.bunge.chat.domain.Message;
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
    private final JsonUtils jsonUtils;

    public void sendMessage(String topic, Message data) throws JsonProcessingException {
        kafkaTemplate.send(topic, jsonUtils.getString(data));
        repository.saveMessage(data);
    }
}





