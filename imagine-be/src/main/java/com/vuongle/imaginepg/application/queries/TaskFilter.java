package com.vuongle.imaginepg.application.queries;

import com.vuongle.imaginepg.domain.constants.TaskColor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskFilter implements Serializable {

    private String likeDescription;
    private boolean pinned;

    private TaskColor color;

    private UUID userId;
}
