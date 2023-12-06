package com.vuongle.imaginepg.domain.services;

import com.vuongle.imaginepg.application.commands.RegisterCommand;
import com.vuongle.imaginepg.application.dto.UserDto;
import com.vuongle.imaginepg.application.queries.UserFilter;

public interface UserService extends BaseService<UserDto, RegisterCommand, UserFilter> {
}
