package com.vuongle.imaginepg.domain.services;

import com.vuongle.imaginepg.application.commands.CreateConversationCommand;
import com.vuongle.imaginepg.application.dto.ConversationDto;
import com.vuongle.imaginepg.application.queries.ConversationFilter;

import java.util.UUID;

public interface ConversationService extends BaseService<ConversationDto, CreateConversationCommand, ConversationFilter> {

    void addUserToGroupChat(UUID conversationId, UUID userId);

}
