package com.vuongle.imaginepg.application.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserConversationCommand implements Serializable {

    private UUID userId;

    private UUID conversationId;

    private boolean notificationsEnabled;

    private boolean mute;

    private String theme;

    private int fontSize;

    private boolean pinned;
}
