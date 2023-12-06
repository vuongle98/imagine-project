package com.vuongle.imaginepg.domain.services;

import java.util.UUID;

public interface ConversationService {

    void addUserToGroupChat(UUID conversationId, UUID userId);

}
