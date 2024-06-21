package com.vuongle.imaginepg.application.commands;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginCommand implements Serializable {

    private String username;
    private String password;
}
