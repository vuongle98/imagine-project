package com.vuongle.imaginepg.domain.entities;

import com.vuongle.imaginepg.domain.constants.ChatType;
import com.vuongle.imaginepg.shared.utils.Context;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.time.Instant;
import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "conversation")
@AllArgsConstructor
@NoArgsConstructor
public class Conversation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String title;

    @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChatMessage> messages = new ArrayList<>();

    private Instant deletedAt;

    private boolean isGroupChat;

    @Enumerated(EnumType.STRING)
    private ChatType type = ChatType.PRIVATE;

    @ManyToMany
    @JoinTable(
            name = "conversation_participants",
            joinColumns = @JoinColumn(name = "conversation_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> participants;

    @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserConversation> userConversations = new HashSet<>();

    @Transient // To avoid persistence
    private UserConversation settings;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // creator

    // Additional conversation settings
    @Column(nullable = false)
    private boolean allowJoinRequests;

    @Column(nullable = false)
    private boolean allowMessageEditing;

    private String description;

    // type: public, private...

    @CreatedBy
    @Column(name = "created_by")
    private String createdBy;

    @CreatedDate
    @Column(name = "created_date")
    private Instant createdAt;

    @LastModifiedDate
    @Column(name = "last_modified_date")
    private Instant lastModifiedDate;

    @LastModifiedBy
    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    public void addParticipant(User user) {
        if (participants == null) {
            participants = new ArrayList<>();
        }

        participants.add(user);
    }

    public void addListParticipants(List<User> users) {
        if (participants == null) {
            participants = new ArrayList<>();
        }

        participants.addAll(users);
    }

    public void removeParticipant(UUID userId) {
        if (participants == null) return;

        participants.removeIf(u -> u.getId().equals(userId));
    }

    public void removeParticipants(List<UUID> userIds) {
        if (participants == null) return;

        for (var id: userIds) {
            removeParticipant(id);
        }
    }

    public boolean hasParticipant(UUID participantId) {
        return participants.stream().anyMatch(p -> p.getId().equals(participantId));
    }

    public boolean isOwner() {
        return user.getId().equals(Context.getUser().getId());
    }
}
