package com.vuongle.imaginepg.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "url_shorten")
public class ShortenUrl implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "creator_id", nullable = false)
    private User user;

    @Column(name = "hashed")
    private String hashed;

    @Column(name = "original_url")
    private String originalUrl;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "expired_date")
    private Instant expiredDate;
}
