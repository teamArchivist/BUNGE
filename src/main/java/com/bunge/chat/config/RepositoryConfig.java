package com.bunge.chat.config;

import com.bunge.chat.repository.ChatMapper;
import com.bunge.chat.repository.ChatRepository;
import com.bunge.chat.repository.MyBatisChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RepositoryConfig {

    private final ChatMapper chatMapper;

    @Bean
    public ChatRepository chatRepository() {
        return new MyBatisChatRepository(chatMapper);
    }
}
