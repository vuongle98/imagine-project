package com.vuongle.imaginepg.application.commands;

import com.vuongle.imaginepg.application.dto.UserDto;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
public class CreateCommentCommand implements Serializable {

    private String content;
    private UserDto creator;

    private UUID postId;

    private UUID parentId;
}
