package com.vuongle.imaginepg.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vuongle.imaginepg.application.exceptions.DataNotFoundException;
import com.vuongle.imaginepg.domain.constants.FriendStatus;
import com.vuongle.imaginepg.domain.constants.Gender;
import com.vuongle.imaginepg.domain.constants.UserRole;
import com.vuongle.imaginepg.domain.entities.store.Book;
import com.vuongle.imaginepg.domain.entities.store.Discount;
import com.vuongle.imaginepg.domain.entities.store.Order;
import com.vuongle.imaginepg.shared.utils.Context;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "app_user"
)
@EntityListeners(AuditingEntityListener.class)
@Builder
public class User implements Serializable, UserDetails {

    @Enumerated(EnumType.STRING)
    protected Set<UserRole> roles = Set.of(UserRole.USER);

    @Id
    @Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "birthday")
    private Instant birthday;

    @Column(name = "gender", nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender = Gender.MALE;

    @Column(name = "address")
    private String address;

    private boolean locked;
    private boolean enabled = true;
    private boolean online;
    private Instant lastActive;
    private String password;

    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Post> posts;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<PostLike> likes;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore
    private Set<Friendship> friendships = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<UserConversation> userConversations = new HashSet<>();

    @OneToMany(mappedBy = "publisher", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Book> publishedBooks;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Order> orders;

    @ManyToMany(mappedBy = "participants", fetch = FetchType.LAZY)
    private List<Conversation> conversations;

    @ManyToMany(mappedBy = "customers", fetch = FetchType.LAZY)
    private List<Discount> discounts;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<ShortenUrl> urlShortens;

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

    public User(String username) {
        this.username = username;
    }

    public User(String username, String fullName) {
        this.username = username;
        this.fullName = fullName;
    }

    public User(
            String username,
            String fullName,
            String email,
            String password,
            List<UserRole> roles
    ) {
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.roles = new HashSet<>(roles);
    }

    public User(
            UUID id,
            String username,
            String email,
            String fullName,
            String password,
            Collection<? extends GrantedAuthority> authorities
    ) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.roles = authorities.stream().map(item -> UserRole.valueOf(item.getAuthority())).collect(Collectors.toSet());
    }

    public void setRoles(List<UserRole> roles) {
        this.roles = new HashSet<>(roles);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.name())).collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !this.isLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return Objects.equals(id, user.getId());
    }

    public boolean isAdmin() {
        return roles.contains(UserRole.ADMIN);
    }

    public boolean isModerator() {
        return roles.contains(UserRole.MODERATOR);
    }

    public boolean hasModifyPermission() {
        return isModerator() || isAdmin();
    }

    private void lockUser(boolean locked) {
        this.setLocked(locked);
    }

    // current user request friend
    public void addFriend(User friend) {
        if (Objects.isNull(friendships)) {
            friendships = new HashSet<>();
        }

        if (friendships.stream().noneMatch(f -> f.getFriend().getId().equals(friend.getId()))) {
            Friendship friendship = new Friendship();
            friendship.setUser(Context.getUser());
            friendship.setFriend(friend);
            friendship.setStatus(FriendStatus.REQUESTED);
            friendship.setFriendFrom(Instant.now());
            friendships.add(friendship);

//            friend.getFriendships().add(new Friendship(friend, this, FriendStatus.PENDING));
        } else {
            for (var friendShip : friendships) {
                if (friendShip.getFriend().getId().equals(friend.getId()) &&
                        (friendShip.getStatus().equals(FriendStatus.REJECTED) || friendShip.getStatus().equals(FriendStatus.REJECTED_REQUEST))) {
                    friendShip.setStatus(FriendStatus.REQUESTED);
                    friendShip.setUpdateFrom(Instant.now());
                }
            }
        }
    }

    // friend receive request
    public void pendingFriend(User friend) {
        if (Objects.isNull(friendships)) {
            friendships = new HashSet<>();
        }

        if (friendships.stream().noneMatch(f -> f.getFriend().getId().equals(friend.getId()))) {
            Friendship friendship = new Friendship();
            friendship.setUser(this);
            friendship.setFriend(friend);
            friendship.setStatus(FriendStatus.PENDING);
            friendship.setFriendFrom(Instant.now());
            friendships.add(friendship);
        } else {
            for (var friendShip : friendships) {
                if (friendShip.getFriend().getId().equals(friend.getId()) &&
                        (friendShip.getStatus().equals(FriendStatus.REJECTED) || friendShip.getStatus().equals(FriendStatus.REJECTED_REQUEST))) {
                    friendShip.setStatus(FriendStatus.PENDING);
                    friendShip.setUpdateFrom(Instant.now());
                }
            }
        }
    }

    public void acceptFriend(User friend) {
        if (Objects.isNull(friendships)) {
            throw new DataNotFoundException("Not found relationship with " + friend.getUsername());
        }

        if (friendships.stream().noneMatch(f -> f.getFriend().getId().equals(friend.getId()))) {
            throw new DataNotFoundException("Not found friendship");
        }

        if (friendships.stream().anyMatch(f -> f.getFriend().getId().equals(friend.getId()) &&
                (f.getStatus().equals(FriendStatus.PENDING) || f.getStatus().equals(FriendStatus.REQUESTED)))) {
            for (var friendShip : friendships) {
                if (friendShip.getFriend().getId().equals(friend.getId())) {
                    friendShip.setStatus(FriendStatus.ACCEPTED);
                    friendShip.setUpdateFrom(Instant.now());
                }
            }
        }
    }

    public void declineFriend(User friend) {
        if (Objects.isNull(friendships)) {
            throw new DataNotFoundException("Not found relationship with " + friend.getUsername());
        }

        if (friendships.stream().noneMatch(f -> f.getFriend().getId().equals(friend.getId()))) {
            throw new DataNotFoundException("Not found friendship");
        }

        if (friendships.stream().anyMatch(f -> f.getFriend().getId().equals(friend.getId()) && f.getStatus().equals(FriendStatus.REQUESTED))) {
            // update
            for (var friendship : friendships) {
                if (friendship.getFriend().getId().equals(friend.getId())) {
                    friendship.setStatus(FriendStatus.REJECTED_REQUEST);
                    friendship.setUpdateFrom(Instant.now());
                }
            }
        }


        if (friendships.stream().anyMatch(f -> f.getFriend().getId().equals(friend.getId()) && f.getStatus().equals(FriendStatus.PENDING))) {
            for (var friendship : friendships) {
                if (friendship.getFriend().getId().equals(friend.getId())) {
                    friendship.setStatus(FriendStatus.REJECTED);
                    friendship.setUpdateFrom(Instant.now());
                }
            }
        }
    }

    public void removeFriend(User friend) {
        if (Objects.isNull(friendships)) {
            throw new DataNotFoundException("Not found relationship with " + friend.getUsername());
        }

        if (friendships.stream().noneMatch(f -> f.getFriend().getId().equals(friend.getId()))) {
            throw new DataNotFoundException("Not found friendship");
        }

        friendships.removeIf(f -> f.getFriend().getId().equals(friend.getId()));
    }

    public boolean isFriend(User friend) {
        return friendships.stream().anyMatch(f -> f.getFriend().getId().equals(friend.getId()) && f.getStatus().equals(FriendStatus.ACCEPTED));
    }


}
