package com.vuongle.imaginepg.application.dto;

import com.vuongle.imaginepg.domain.constants.QuestionLevel;
import com.vuongle.imaginepg.domain.entities.Question;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizDto {

    private UUID id;

    private String title;

    private String description;

    private List<QuestionDto> questions;

    private FileDto coverImage;

    private QuestionLevel level;

    private boolean published;
}
