package com.vuongle.imaginepg.application.commands;

import com.vuongle.imaginepg.domain.constants.QuestionCategory;
import com.vuongle.imaginepg.domain.constants.QuestionLevel;
import com.vuongle.imaginepg.domain.constants.QuestionType;
import com.vuongle.imaginepg.domain.entities.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateQuestionCommand implements Serializable {

    private String title;

    private List<UUID> answerIds;

    private String description = "";

    private int countdown;

    private QuestionLevel level = QuestionLevel.EASY;

    private boolean mark;

    private QuestionType type;

    private QuestionCategory category;
}
