package com.vuongle.imaginepg.domain.services;

import com.vuongle.imaginepg.application.commands.CreateQuizCommand;
import com.vuongle.imaginepg.application.commands.SubmitAnswer;
import com.vuongle.imaginepg.application.dto.AnswerQuizResult;
import com.vuongle.imaginepg.application.dto.QuizDto;
import com.vuongle.imaginepg.application.queries.QuizFilter;

import java.util.List;
import java.util.UUID;

public interface QuizService extends BaseService<QuizDto, CreateQuizCommand, QuizFilter> {

    AnswerQuizResult answerQuiz(UUID quizId, List<SubmitAnswer> answers);
}
