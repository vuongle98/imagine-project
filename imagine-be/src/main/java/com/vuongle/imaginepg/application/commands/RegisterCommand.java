package com.vuongle.imaginepg.application.commands;

import lombok.*;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterCommand extends LoginCommand implements Serializable {

    private String email;
    private String fullName;

    public RegisterCommand(String username, String password, String email, String fullName) {
        super(username, password);
        this.email = email;
        this.fullName = fullName;
    }
}
