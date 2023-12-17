package com.vuongle.imaginepg.domain.services;

import com.vuongle.imaginepg.application.commands.CreatePostLikeCommand;
import com.vuongle.imaginepg.application.dto.PostLikeDto;
import com.vuongle.imaginepg.application.queries.PostLikeFilter;

import java.util.UUID;

public interface PostLikeService extends BaseService<PostLikeDto, CreatePostLikeCommand, PostLikeFilter> {

    void unlikePost(UUID postId);

}
