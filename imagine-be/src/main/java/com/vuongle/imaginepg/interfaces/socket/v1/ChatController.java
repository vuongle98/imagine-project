package com.vuongle.imaginepg.interfaces.socket.v1;

import com.vuongle.imaginepg.application.commands.CreateChatMessageCommand;
import com.vuongle.imaginepg.application.dto.ChatMessageDto;
import com.vuongle.imaginepg.application.exceptions.DataNotFoundException;
import com.vuongle.imaginepg.application.exceptions.UnAuthorizationException;
import com.vuongle.imaginepg.application.exceptions.UserNotFoundException;
import com.vuongle.imaginepg.domain.entities.ChatMessage;
import com.vuongle.imaginepg.domain.entities.Conversation;
import com.vuongle.imaginepg.domain.entities.User;
import com.vuongle.imaginepg.domain.repositories.ChatMessageRepository;
import com.vuongle.imaginepg.domain.repositories.ConversationRepository;
import com.vuongle.imaginepg.domain.services.ChatMessageService;
import com.vuongle.imaginepg.shared.utils.ObjectData;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Controller
public class ChatController {

    private final ChatMessageRepository messageRepository;

    private final ConversationRepository conversationRepository;

    private final ChatMessageService messageService;

    public ChatController(
            ChatMessageRepository messageRepository,
            ConversationRepository conversationRepository,
            ChatMessageService messageService
    ) {
        this.messageRepository = messageRepository;
        this.conversationRepository = conversationRepository;
        this.messageService = messageService;
    }

    @MessageMapping("/chat/public")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {

        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) headerAccessor.getUser();

        if (authenticationToken == null) {
//            chatMessage.setSender(new User("anonymous", "anonymous"));
//            return chatMessage;

            throw new UnAuthorizationException("No authentication");
        }

        if (authenticationToken.getPrincipal() instanceof User user) {
            chatMessage.setSender(user);
        }

        if (chatMessage.getSender() == null) {
            throw new DataNotFoundException("No sender");
        }

        messageRepository.save(chatMessage);

        return chatMessage;
    }

    @MessageMapping("/chat/{conversationId}")
    @SendTo("/topic/{conversationId}")
    @Transactional
    public ChatMessageDto sendMessage(
            @DestinationVariable("conversationId") UUID conversationId,
            @Payload CreateChatMessageCommand command,
            SimpMessageHeaderAccessor headerAccessor
    ) {

        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) headerAccessor.getUser();

        if (authenticationToken == null) {
            throw new UnAuthorizationException("No authentication");
        }

        Conversation conversation = conversationRepository.getById(conversationId);

//        if (authenticationToken.getPrincipal() instanceof String username) {
//            chatMessage.setSender(new User("anonymous-" + username, "anonymous-" + username));
//        }

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setContent(command.getContent());

        if (authenticationToken.getPrincipal() instanceof User user) {
            // Add username in web socket session
            chatMessage.setSender(user);
        }

        chatMessage.setConversation(conversation);

        if (chatMessage.getSender() == null) {
            throw new UserNotFoundException("No sender");
        }

        messageRepository.save(chatMessage);
        return ObjectData.mapTo(chatMessage, ChatMessageDto.class);

    }
}
