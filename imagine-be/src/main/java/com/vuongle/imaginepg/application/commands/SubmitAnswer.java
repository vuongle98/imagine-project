package com.vuongle.imaginepg.application.commands;

import com.vuongle.imaginepg.application.dto.QuestionDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmitAnswer implements Serializable {

    private UUID questionId;

    private List<UUID> answerIds;

    private List<UUID> correctAnswerIds;

    private QuestionDto question;

}
