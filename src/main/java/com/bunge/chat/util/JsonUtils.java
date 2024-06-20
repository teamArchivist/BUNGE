package com.bunge.chat.util;

import com.bunge.chat.domain.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JsonUtils {

    private final ObjectMapper mapper;

    public String getString(Object object) throws JsonProcessingException {
        return mapper.writeValueAsString(object);
    }

    public Message getObject(String payload) throws JsonProcessingException {
        return mapper.readValue(payload, Message.class);
    }
}
