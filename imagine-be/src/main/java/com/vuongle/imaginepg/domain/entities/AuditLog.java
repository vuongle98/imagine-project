package com.vuongle.imaginepg.domain.entities;

import com.vuongle.imaginepg.domain.constants.EventType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Table(name = "audit_log")
@Getter
@Setter
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class AuditLog implements Serializable {

    @Id
    private UUID id;

    private EventType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", nullable = false)
    private User creator;

    private String tableName;

    private String recordId;

    private Instant createdAt;

    private String description;
}
