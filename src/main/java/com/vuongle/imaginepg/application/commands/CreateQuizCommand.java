package com.vuongle.imaginepg.application.commands;

import com.vuongle.imaginepg.domain.constants.QuestionLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateQuizCommand implements Serializable {

    private String title;
    private String description;
    private List<UUID> addQuestionIds;
    private List<UUID> removeQuestionIds;
    private QuestionLevel level;
    private boolean mark;
    private boolean published;

    private UUID fileId;
}
