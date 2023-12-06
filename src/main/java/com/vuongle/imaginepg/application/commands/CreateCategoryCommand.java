package com.vuongle.imaginepg.application.commands;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
public class CreateCategoryCommand implements Serializable {

    private String name;
}
