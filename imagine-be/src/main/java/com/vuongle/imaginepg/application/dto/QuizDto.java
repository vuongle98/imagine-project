package com.vuongle.imaginepg.application.dto;

import com.vuongle.imaginepg.domain.constants.QuestionLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
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

    private Instant publishedAt;
}
