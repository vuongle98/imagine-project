package com.vuongle.imaginepg.domain.services;

import com.vuongle.imaginepg.application.commands.CreateTagCommand;
import com.vuongle.imaginepg.application.dto.TagDto;
import com.vuongle.imaginepg.application.queries.TagFilter;

public interface TagService extends BaseService<TagDto, CreateTagCommand, TagFilter> {

}
