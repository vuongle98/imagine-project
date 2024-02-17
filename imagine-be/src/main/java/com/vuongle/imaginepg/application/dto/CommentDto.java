package com.vuongle.imaginepg.application.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
public class CommentDto implements Serializable {

    private UUID id;
    private String content;
    private UserDto creator;
}
