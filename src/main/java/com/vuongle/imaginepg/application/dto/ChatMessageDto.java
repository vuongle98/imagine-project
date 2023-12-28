package com.vuongle.imaginepg.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDto implements Serializable {

    private UUID id;

    private ConversationDto conversation;

    private String content;

    private UserDto sender;

    private Instant createdAt;

    public ChatMessageDto(
            UserDto sender,
            String content
    ) {
        this.sender = sender;
        this.content = content;
    }

}
