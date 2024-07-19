package com.vuongle.imaginepg.domain.services;

import com.vuongle.imaginepg.application.commands.CreateAnswerCommand;
import com.vuongle.imaginepg.application.dto.AnswerDto;
import com.vuongle.imaginepg.application.queries.AnswerFilter;

public interface AnswerService extends BaseService<AnswerDto, CreateAnswerCommand>,
        BaseQueryService<AnswerDto, AnswerFilter> {
}
