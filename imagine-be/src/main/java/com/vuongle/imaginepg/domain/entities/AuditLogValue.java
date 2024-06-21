package com.vuongle.imaginepg.domain.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Table(name = "audit_log_value")
@Getter
@Setter
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class AuditLogValue {

  @Id
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "audit_id", nullable = false)
  private AuditLog auditLog;

  private String fieldName;

  private String PreviousValue;

  private String NewValue;

}
