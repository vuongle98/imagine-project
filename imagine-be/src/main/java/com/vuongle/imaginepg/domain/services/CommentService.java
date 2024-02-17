package com.vuongle.imaginepg.domain.services;

import com.vuongle.imaginepg.application.commands.CreateCommentCommand;
import com.vuongle.imaginepg.application.dto.CommentDto;
import com.vuongle.imaginepg.application.queries.CommentFilter;

public interface CommentService extends BaseService<CommentDto, CreateCommentCommand, CommentFilter> {
}
