package com.vuongle.imaginepg.application.commands;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class LoginCommand implements Serializable {

    private String username;
    private String password;
}
