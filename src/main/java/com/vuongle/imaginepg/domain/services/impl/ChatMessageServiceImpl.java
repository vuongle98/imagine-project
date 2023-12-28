package com.vuongle.imaginepg.domain.services.impl;

import com.vuongle.imaginepg.application.commands.CreateChatMessageCommand;
import com.vuongle.imaginepg.application.dto.ChatMessageDto;
import com.vuongle.imaginepg.application.exceptions.NoPermissionException;
import com.vuongle.imaginepg.application.queries.ChatMessageFilter;
import com.vuongle.imaginepg.domain.entities.ChatMessage;
import com.vuongle.imaginepg.domain.entities.Conversation;
import com.vuongle.imaginepg.domain.repositories.BaseRepository;
import com.vuongle.imaginepg.domain.services.ChatMessageService;
import com.vuongle.imaginepg.infrastructure.specification.ChatMessageSpecifications;
import com.vuongle.imaginepg.shared.utils.Context;
import com.vuongle.imaginepg.shared.utils.ObjectData;
import com.vuongle.imaginepg.shared.utils.ValidateResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

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
        return getById(id, ChatMessageDto.class);
    }

    @Override
    public <R> R getById(UUID id, Class<R> classType) {
        ChatMessage chatMessage = messageRepository.getById(id);

        // check permission
        if (!Context.hasModifyPermission() && !ValidateResource.isOwnResource(chatMessage, ChatMessage.class)) {
            throw new NoPermissionException("No permission");
        }

        return ObjectData.mapTo(chatMessage, classType);
    }

    @Override
    public ChatMessageDto create(CreateChatMessageCommand command) {

        ChatMessage chatMessage = ObjectData.mapTo(command, ChatMessage.class);

        Conversation conversation = conversationRepository.getById(command.getConversationId());
        chatMessage.setConversation(conversation);
        chatMessage.setSender(Context.getUser());

        chatMessage = messageRepository.save(chatMessage);

        return ObjectData.mapTo(chatMessage, ChatMessageDto.class);
    }

    @Override
    public ChatMessageDto update(UUID id, CreateChatMessageCommand command) {

        ChatMessage chatMessage = getById(id, ChatMessage.class);

        if (Objects.nonNull(command.getContent())) {
            chatMessage.setContent(command.getContent());
        }

        chatMessage = messageRepository.save(chatMessage);

        return ObjectData.mapTo(chatMessage, ChatMessageDto.class);
    }

    @Override
    public void delete(UUID id, boolean force) {

        ChatMessage chatMessage = getById(id, ChatMessage.class);

        if (force) {
            messageRepository.deleteById(id);
            return;
        }

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

    @Override
    public Page<ChatMessageDto> findLatestMessage(ChatMessageFilter filter, Pageable pageable) {
        Page<ChatMessageDto> messagePage = getAll(filter, pageable);
        List<ChatMessageDto> messages = messagePage.getContent().stream().sorted(Comparator.comparing(ChatMessageDto::getCreatedAt)).collect(Collectors.toList());

        return new PageImpl<>(messages, pageable, messagePage.getTotalElements());

    }
}
