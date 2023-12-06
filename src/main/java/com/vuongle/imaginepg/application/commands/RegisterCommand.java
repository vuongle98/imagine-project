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
}
