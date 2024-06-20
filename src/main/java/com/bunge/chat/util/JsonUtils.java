package com.bunge.chat.util;

import com.bunge.chat.domain.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class JsonUtils {

    public static String getString(Object object) throws JsonProcessingException {
        return objectMapper().writeValueAsString(object);
    }

    public static Message getObject(String payload) throws JsonProcessingException {
        return objectMapper().readValue(payload, Message.class);
    }

    static ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }
}
