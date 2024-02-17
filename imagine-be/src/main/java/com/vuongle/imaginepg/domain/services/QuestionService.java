package com.vuongle.imaginepg.domain.services;

import com.vuongle.imaginepg.application.commands.CreateQuestionCommand;
import com.vuongle.imaginepg.application.dto.QuestionDto;
import com.vuongle.imaginepg.application.queries.QuestionFilter;

public interface QuestionService extends BaseService<QuestionDto, CreateQuestionCommand, QuestionFilter> {
}
