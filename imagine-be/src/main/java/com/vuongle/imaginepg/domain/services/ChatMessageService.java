package com.vuongle.imaginepg.domain.services;

import com.vuongle.imaginepg.application.commands.CreateChatMessageCommand;
import com.vuongle.imaginepg.application.dto.ChatMessageDto;
import com.vuongle.imaginepg.application.queries.ChatMessageFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ChatMessageService extends BaseService<ChatMessageDto, CreateChatMessageCommand, ChatMessageFilter> {

    Page<ChatMessageDto> findLatestMessage(ChatMessageFilter filter, Pageable pageable);

}
