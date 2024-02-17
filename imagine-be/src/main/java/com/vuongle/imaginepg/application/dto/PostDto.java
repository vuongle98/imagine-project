package com.vuongle.imaginepg.application.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
public class PostDto implements Serializable {

    private UUID id;
    private String title;
    private String slug;
    private String description;
    private String content;
    private CategoryDto category;
    private UserDto creator;
    private int numLikes;
    private int numComments;
    private Instant publishedAt;
    private Instant createdAt;
}
