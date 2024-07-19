package com.vuongle.imaginepg.domain.sockets;

import com.vuongle.imaginepg.application.dto.ChatMessageDto;
import com.vuongle.imaginepg.application.dto.UserDto;
import com.vuongle.imaginepg.domain.entities.User;
import com.vuongle.imaginepg.domain.mappers.UserMapper;
import com.vuongle.imaginepg.domain.repositories.ConversationRepository;
import com.vuongle.imaginepg.domain.services.ChatMessageService;
import com.vuongle.imaginepg.domain.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;

import java.security.Principal;
import java.util.Objects;

@Component
@Slf4j
public class WebSocketEventListener {

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @Autowired
    private SimpUserRegistry simpUserRegistry;

    @Autowired
    private UserService userService;

    @Autowired
    private ConversationRepository conversationRepository;

    @Autowired
    private ChatMessageService messageService;
    @Value("${imagine.app.recent-message-limit}")
    private int recentMessageLimit;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {

        log.info("Received a new web socket connection");
        User user = getUser(event.getUser());

        if (Objects.isNull(user)) {
        }
        // set user online
//        userService.setUserOnline(user.getId(), true);
    }

    @EventListener
    @Transactional
    public void handleSessionSubscribeEvent(SessionSubscribeEvent event) {

        // TODO: check trùng user
        // TODO: check nếu user subscribe lần 2

        GenericMessage<?> message = (GenericMessage<?>) event.getMessage();
        String simpDestination = (String) message.getHeaders().get("simpDestination");

        if (simpDestination == null) {
            return;
        }

        Principal principal = event.getUser();

        if (simpDestination.startsWith("/topic")) {

            String conversationStr = simpDestination.replace("/topic/", "");

            if (principal instanceof UsernamePasswordAuthenticationToken token) {

                if (token.getPrincipal() instanceof User user) {

                    if (conversationStr.equals("public")) {

                        ChatMessageDto chatMessage = new ChatMessageDto();
                        chatMessage.setContent("Welcome to public channel");
                        chatMessage.setSender(new UserDto("admin", "admin"));

                        messagingTemplate.convertAndSendToUser(user.getUsername(), simpDestination, chatMessage);
                        return;
                    }

//                    UUID conversationId = UUID.fromString(conversationStr);
//                    Conversation conversation = conversationRepository.getById(conversationId);
//
//                    if (!conversation.hasParticipant(user.getId())) {
//                        return;
//                    }
//
//                    ChatMessageFilter filter = new ChatMessageFilter();
//                    filter.setConversationId(conversationId);
//
//                    Pageable pageable = PageRequest.of(0, 20, Sort.by("id").ascending());
//
//                    Page<ChatMessageDto> messages = messageService.findLatestMessage(filter, pageable);

                    ChatMessageDto chatMessage = new ChatMessageDto();
                    chatMessage.setContent("Welcome back");
                    chatMessage.setSender(new UserDto("admin", "admin"));
                    messagingTemplate.convertAndSendToUser(user.getUsername(), simpDestination, chatMessage);
                }

                if (token.getPrincipal() instanceof String username) {

                    if (!conversationStr.equals("public")) {
                        return;
                    }

                    log.info("Public channel with username register");
                }
            } else {

                if (!conversationStr.equals("public")) {
                    return;
                }
                // anonymous join to public channel
                User anonymous = new User();
                anonymous.setUsername("anonymous");
                anonymous.setFullName("anonymous");
                messagingTemplate.convertAndSendToUser("anonymous", simpDestination, new ChatMessageDto(UserMapper.mapToDto(anonymous), "Hello"));
            }
        }
    }

    @EventListener
    public void handleSessionUnSubscribeEvent(SessionUnsubscribeEvent event) {

        Principal principal = event.getUser();

        if (principal instanceof UsernamePasswordAuthenticationToken token) {

            if (token.getPrincipal() instanceof User user) {
                System.out.println(user.getUsername() + " unsubscribed");
            }
        }
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        User user = getUser(headerAccessor.getUser());

        if (Objects.isNull(user)) {
            return;
        }

        // set user online
//        userService.setUserOnline(user.getId(), false);

        String username = (String) headerAccessor.getSessionAttributes().get("username");
        if (username != null) {
            log.info("User Disconnected : " + username);

            messagingTemplate.convertAndSend("/topic/public", "bye");
        }
    }

    private boolean isUserLoggedIn(String username) {
        return simpUserRegistry.getUsers().stream().anyMatch(u -> u.getName().equals(username));

    }

    private User getUser(Principal principal) {
        if (principal instanceof UsernamePasswordAuthenticationToken token) {
            if (token.getPrincipal() instanceof User user) {
                return user;
            }
        }
        return null;
    }

    private String getUsername(Principal principal) {
        if (principal instanceof UsernamePasswordAuthenticationToken token) {
            if (token.getPrincipal() instanceof String username) {
                return username;
            }
        }
        return null;
    }
}
