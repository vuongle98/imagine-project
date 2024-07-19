package com.vuongle.imaginepg.domain.services;

import com.vuongle.imaginepg.application.commands.CreateTaskCommand;
import com.vuongle.imaginepg.application.dto.TaskDto;
import com.vuongle.imaginepg.application.queries.TaskFilter;

public interface TodoService extends BaseService<TaskDto, CreateTaskCommand>, BaseQueryService<TaskDto, TaskFilter> {
}
