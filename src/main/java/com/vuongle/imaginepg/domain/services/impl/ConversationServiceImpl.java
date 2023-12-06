package com.vuongle.imaginepg.domain.services.impl;

import com.vuongle.imaginepg.domain.services.ConversationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class ConversationServiceImpl implements ConversationService {


    @Override
    public void addUserToGroupChat(UUID conversationId, UUID userId) {

    }
}
