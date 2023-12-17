package com.vuongle.imaginepg.domain.entities;

import com.vuongle.imaginepg.domain.constants.FriendStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "friendships")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Friendship {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "friend_id", nullable = false)
    private User friend;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FriendStatus status;

    private Instant updateFrom = Instant.now();

    private Instant friendFrom = Instant.now();

    public Friendship(User user, User friend, FriendStatus status) {
        this.user = user;
        this.friend = friend;
        this.status = status;
    }

}
