package com.vuongle.imaginepg.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConversationDto implements Serializable {

    private UUID id;
    private String title;
    private boolean isGroupChat;

    private List<UserDto> participants;

    private boolean allowJoinRequests;

    private boolean allowMessageEditing;
}
