package com.vuongle.imaginepg.domain.entities.log;

import com.vuongle.imaginepg.domain.entities.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Table(name = "login_log")
@Getter
@Setter
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class LoginLog {

    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private Instant createdAt;

    private String clientIp;

    private String userAgent;

}
