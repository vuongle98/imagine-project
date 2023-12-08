package com.vuongle.imaginepg.domain.services.impl;

import com.vuongle.imaginepg.application.commands.CreateChatMessageCommand;
import com.vuongle.imaginepg.application.dto.ChatMessageDto;
import com.vuongle.imaginepg.application.queries.ChatMessageFilter;
import com.vuongle.imaginepg.domain.entities.ChatMessage;
import com.vuongle.imaginepg.domain.entities.Conversation;
import com.vuongle.imaginepg.domain.repositories.BaseRepository;
import com.vuongle.imaginepg.domain.services.ChatMessageService;
import com.vuongle.imaginepg.infrastructure.specification.ChatMessageSpecifications;
import com.vuongle.imaginepg.shared.utils.Context;
import com.vuongle.imaginepg.shared.utils.ObjectData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@Transactional
public class ChatMessageServiceImpl implements ChatMessageService {

    private final BaseRepository<ChatMessage> messageRepository;

    private final BaseRepository<Conversation> conversationRepository;

    public ChatMessageServiceImpl(
            BaseRepository<ChatMessage> messageRepository,
            BaseRepository<Conversation> conversationRepository
    ) {
        this.messageRepository = messageRepository;
        this.conversationRepository = conversationRepository;
    }

    @Override
    public ChatMessageDto getById(UUID id) {
        return ObjectData.mapTo(messageRepository.getById(id), ChatMessageDto.class);
    }

    @Override
    public ChatMessageDto create(CreateChatMessageCommand command) {

        ChatMessage chatMessage = ObjectData.mapTo(command, ChatMessage.class);

        Conversation conversation = conversationRepository.getById(command.getConversationId());
        chatMessage.setConversation(conversation);
        chatMessage.setUser(Context.getUser());

        chatMessage = messageRepository.save(chatMessage);

        return ObjectData.mapTo(chatMessage, ChatMessageDto.class);
    }

    @Override
    public ChatMessageDto update(UUID id, CreateChatMessageCommand command) {

        ChatMessage chatMessage = messageRepository.getById(id);

        if (Objects.nonNull(command.getContent())) {
            chatMessage.setContent(command.getContent());
        }

        chatMessage = messageRepository.save(chatMessage);

        return ObjectData.mapTo(chatMessage, ChatMessageDto.class);
    }

    @Override
    public void delete(UUID id, boolean force) {

        if (force) messageRepository.deleteById(id);
    }

    @Override
    public Page<ChatMessageDto> getAll(ChatMessageFilter filter, Pageable pageable) {
        Specification<ChatMessage> specification = ChatMessageSpecifications.withFilter(filter);

        Page<ChatMessage> chatMessagePage = messageRepository.findAll(specification, pageable);
        return chatMessagePage.map(c -> ObjectData.mapTo(c, ChatMessageDto.class));
    }

    @Override
    public List<ChatMessageDto> getAll(ChatMessageFilter filter) {
        Specification<ChatMessage> specification = ChatMessageSpecifications.withFilter(filter);

        return ObjectData.mapListTo(messageRepository.findAll(specification), ChatMessageDto.class);
    }
}
