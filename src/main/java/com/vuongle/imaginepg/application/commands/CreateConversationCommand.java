package com.vuongle.imaginepg.application.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateConversationCommand implements Serializable {

    private String title;
    private boolean isGroupChat;

    private List<UUID> addParticipants;
    private List<UUID> removeParticipants;

    private boolean allowJoinRequests;

    private boolean allowMessageEditing;
}
