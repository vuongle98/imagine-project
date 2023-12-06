package com.vuongle.imaginepg.application.commands;

import com.vuongle.imaginepg.domain.constants.TaskColor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTaskCommand implements Serializable {

    private String description;

    private Instant dueDate;

    private TaskColor color = TaskColor.NONE;

    private boolean completed;

    private boolean pinned;
}
