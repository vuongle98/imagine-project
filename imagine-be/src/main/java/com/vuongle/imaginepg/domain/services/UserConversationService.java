package com.vuongle.imaginepg.domain.services;

import com.vuongle.imaginepg.application.commands.CreateUserConversationCommand;
import com.vuongle.imaginepg.application.dto.UserConversationDto;
import com.vuongle.imaginepg.application.queries.UserConversationFilter;
import com.vuongle.imaginepg.domain.entities.Conversation;
import com.vuongle.imaginepg.domain.entities.User;
import com.vuongle.imaginepg.domain.entities.UserConversation;

public interface UserConversationService extends BaseService<UserConversationDto, CreateUserConversationCommand>, BaseQueryService<UserConversationDto, UserConversationFilter> {

    UserConversation create(User user, Conversation conversation);

    <T> T getByFilter(UserConversationFilter filter, Class<T> classType);

    UserConversationDto getByFilter(UserConversationFilter filter);
}
