package com.vuongle.imaginepg.domain.services;

import com.vuongle.imaginepg.application.commands.LoginCommand;
import com.vuongle.imaginepg.application.commands.RegisterCommand;
import com.vuongle.imaginepg.application.dto.JwtResponse;
import com.vuongle.imaginepg.application.dto.UserDto;
import com.vuongle.imaginepg.application.dto.UserProfile;

public interface AuthService {

    JwtResponse login(LoginCommand command);

    UserDto register(RegisterCommand command);

    UserProfile verify();

}
