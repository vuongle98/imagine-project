package com.vuongle.imaginepg.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
public class PostLikeDto implements Serializable {

    private UUID id;

    private UserDto user;

    private PostDto post;

    private Instant createdAt;
}
