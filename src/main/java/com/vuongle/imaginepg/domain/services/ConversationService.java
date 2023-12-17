package com.vuongle.imaginepg.domain.services;

import com.vuongle.imaginepg.application.commands.CreateConversationCommand;
import com.vuongle.imaginepg.application.dto.ConversationDto;
import com.vuongle.imaginepg.application.queries.ConversationFilter;
import com.vuongle.imaginepg.domain.entities.Conversation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ConversationService extends BaseService<ConversationDto, CreateConversationCommand, ConversationFilter> {

    boolean addUserToGroupChat(UUID conversationId, UUID userId);

    boolean removeUserFromGroupChat(UUID conversationId, UUID userId);

    Conversation getByFilter(ConversationFilter filter);

    Page<ConversationDto> getAllByCurrentUser(Pageable pageable);

}
