package com.vuongle.imaginepg.application.dto;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserConversationDto implements Serializable {

    @Id
    private UUID id;

    private boolean notificationsEnabled;

    private boolean mute;

    private String theme;

    private int fontSize;

    private boolean pinned;
}
