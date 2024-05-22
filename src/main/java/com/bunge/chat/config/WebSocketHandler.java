package com.bunge.chat.config;

import com.bunge.chat.domain.Message;
import com.bunge.chat.domain.Utils;
import jdk.jshell.execution.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class WebSocketHandler extends TextWebSocketHandler {

    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        String sessionId = session.getId();
        sessions.put(sessionId, session);

        Message message = Message.builder()
                .sender(sessionId)
                .receiver("all")
                .build();
        message.newConnect();

        sessions.values().forEach(s -> {
            try {
                if (!s.getId().equals(sessionId)) {
                    s.sendMessage(new TextMessage(Utils.getString(message)));
                }
            } catch (Exception e) {
                //TODO: throw
            }
        });
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage textMessage) throws Exception {
        log.info("textMessage={}", textMessage);
        Message message = Utils.getObject(textMessage.getPayload());

        message.setSender(session.getId());

        log.info("receiver: " + message.getReceiver());
        WebSocketSession receiver = sessions.get(message.getReceiver());

        if (receiver != null && receiver.isOpen()) {
            receiver.sendMessage(new TextMessage(Utils.getString(message)));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        super.handleTransportError(session, exception);
    }
}
