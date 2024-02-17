package com.vuongle.imaginepg.application.queries;

import com.vuongle.imaginepg.domain.constants.ChatType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConversationFilter implements Serializable {

    private UUID id;

    private UUID userId;

    private String likeTitle;

    private List<UUID> equalParticipantIds;

    private List<UUID> inParticipantIds;

    private ChatType type;

    private boolean isGroupChat;
}
