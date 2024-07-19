package com.vuongle.imaginepg.application.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateBookAuthorCommand implements Serializable {

    private String fullName;

    private String email;

    private String phone;

    private String address;

}
