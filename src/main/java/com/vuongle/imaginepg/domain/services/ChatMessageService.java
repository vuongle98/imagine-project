package com.vuongle.imaginepg.domain.services;

import com.vuongle.imaginepg.application.commands.CreateChatMessageCommand;
import com.vuongle.imaginepg.application.dto.ChatMessageDto;
import com.vuongle.imaginepg.application.queries.ChatMessageFilter;

public interface ChatMessageService extends BaseService<ChatMessageDto, CreateChatMessageCommand, ChatMessageFilter> {
}
