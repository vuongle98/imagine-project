package com.vuongle.imaginepg.application.commands;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
public class CreatePostCommand implements Serializable {

    private UUID id;

    private String title;
    private String content;
    private UUID categoryId;

    private String description;
}
