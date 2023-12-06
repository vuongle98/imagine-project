package com.vuongle.imaginepg.domain.sockets;

import com.vuongle.imaginepg.domain.entities.User;
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

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {

        log.info("Received a new web socket connection");
        User user = getUser(event.getUser());

        if (Objects.isNull(user)) {
            return;
        }
        // set user online
//        userService.setUserOnline(user.getId(), true);
    }

    @Value("${imagine.app.recent-message-limit}")
    private int recentMessageLimit;

    @EventListener
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

                        messagingTemplate.convertAndSendToUser(user.getUsername(), simpDestination, "Hello");
                        return;
                    }
                }

                if (token.getPrincipal() instanceof String username) {

                    log.info("Public channel with username register");
                }
            } else {

                if (!conversationStr.equals("public")) {
                    return;
                }
                // anonymous join to public channel
                messagingTemplate.convertAndSendToUser("anonymous", simpDestination, "hello");
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
