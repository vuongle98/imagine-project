package com.vuongle.imaginepg.domain.entities;

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

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  private Instant createdAt;

  private String clientIp;

  private String userAgent;

}
