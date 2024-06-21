package com.vuongle.imaginepg.application.commands;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateCategoryCommand implements Serializable {

    private String name;
}
