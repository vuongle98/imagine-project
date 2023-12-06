package com.vuongle.imaginepg.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;
import java.util.Set;
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

    @JsonIgnore
    private Set<UserDto> likedByUsers;

    public int getNumLikes() {
        return likedByUsers == null ? 0 : likedByUsers.size();
    }
}
