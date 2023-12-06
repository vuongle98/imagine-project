package com.vuongle.imaginepg.domain.services;

import com.vuongle.imaginepg.application.commands.CreatePostCommand;
import com.vuongle.imaginepg.application.dto.PostDto;
import com.vuongle.imaginepg.application.queries.PostFilter;
import com.vuongle.imaginepg.domain.entities.Post;

import java.util.List;

public interface PostService extends BaseService<PostDto, CreatePostCommand, PostFilter> {


}
