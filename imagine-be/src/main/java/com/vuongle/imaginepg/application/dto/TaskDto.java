package com.vuongle.imaginepg.application.dto;

import com.vuongle.imaginepg.domain.constants.TaskColor;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class TaskDto implements Serializable {

    private UUID id;
    private String description;
    private Instant dueDate;

    @Enumerated(EnumType.STRING)
    private TaskColor color = TaskColor.NONE;

    private UserDto user;

    private Instant completedAt;

    private boolean pinned;
}
