package com.vuongle.imaginepg.application.dto;

import com.vuongle.imaginepg.domain.constants.QuestionCategory;
import com.vuongle.imaginepg.domain.constants.QuestionLevel;
import com.vuongle.imaginepg.domain.constants.QuestionType;
import com.vuongle.imaginepg.domain.entities.User;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDto implements Serializable {


    private UUID id;

    private String title;

    private List<AnswerDto> answers;

    private UserDto user;

    private String description;

    private int countdown;

    private QuestionLevel level;

    private boolean mark;

    private QuestionType type;

    private QuestionCategory category;

}
