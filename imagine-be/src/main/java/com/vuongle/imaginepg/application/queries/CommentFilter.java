package com.vuongle.imaginepg.application.queries;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
public class CommentFilter implements Serializable {
    private String likeContent;
    private UUID id;
    private UUID postId;

    private String likeAnswer;
}
