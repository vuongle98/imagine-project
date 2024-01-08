package com.vuongle.imaginepg.application.dto;

import com.vuongle.imaginepg.application.commands.SubmitAnswer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerQuizResult implements Serializable {

    private int numOfCorrectAnswers;
    private int totalAnswers;
    private List<SubmitAnswer> answers;
}
