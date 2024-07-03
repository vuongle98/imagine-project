package com.vuongle.imaginepg.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "user_conversation")
@AllArgsConstructor
@NoArgsConstructor
public class UserConversation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conversation_id", nullable = false)
    private Conversation conversation;

    private boolean notificationsEnabled;

    private boolean mute;

    private String theme;

    private int fontSize;

    private boolean pinned;

    public UserConversation(
            User user, Conversation conversation
    ) {
        this.user = user;
        this.conversation = conversation;
    }

}
