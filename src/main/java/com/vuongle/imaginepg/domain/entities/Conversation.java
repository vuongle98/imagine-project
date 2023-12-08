package com.vuongle.imaginepg.domain.entities;

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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL)
    private List<ChatMessage> messages = new ArrayList<>();

    private Instant deletedAt;

    private boolean isGroupChat;

    @ManyToMany
    @JoinTable(
            name = "conversation_participants",
            joinColumns = @JoinColumn(name = "conversation_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> participants;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Additional conversation settings
    @Column(nullable = false)
    private boolean allowJoinRequests;

    @Column(nullable = false)
    private boolean allowMessageEditing;

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
}
