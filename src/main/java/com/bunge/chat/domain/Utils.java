package com.bunge.chat.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Utils {

    public static String getString(Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }

    public static Message getObject(String payload) throws JsonProcessingException {
        return new ObjectMapper().readValue(payload, Message.class);
    }
}
