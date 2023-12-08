package com.vuongle.imaginepg.domain.services;

import com.vuongle.imaginepg.application.commands.CreateQuizCommand;
import com.vuongle.imaginepg.application.dto.QuizDto;
import com.vuongle.imaginepg.application.queries.QuizFilter;

public interface QuizService extends BaseService<QuizDto, CreateQuizCommand, QuizFilter> {
}
