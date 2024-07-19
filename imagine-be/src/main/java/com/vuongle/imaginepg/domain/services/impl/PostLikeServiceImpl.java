package com.vuongle.imaginepg.domain.services.impl;

import com.vuongle.imaginepg.application.commands.CreatePostLikeCommand;
import com.vuongle.imaginepg.application.dto.PostLikeDto;
import com.vuongle.imaginepg.application.exceptions.UserNotFoundException;
import com.vuongle.imaginepg.application.queries.PostLikeFilter;
import com.vuongle.imaginepg.domain.entities.Post;
import com.vuongle.imaginepg.domain.entities.PostLike;
import com.vuongle.imaginepg.domain.entities.User;
import com.vuongle.imaginepg.domain.repositories.PostLikeRepository;
import com.vuongle.imaginepg.domain.services.PostLikeService;
import com.vuongle.imaginepg.domain.services.PostService;
import com.vuongle.imaginepg.shared.utils.Context;
import com.vuongle.imaginepg.shared.utils.ObjectData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class PostLikeServiceImpl implements PostLikeService {

    private final PostLikeRepository postLikeRepository;

    private final PostService postService;

    public PostLikeServiceImpl(
            PostLikeRepository postLikeRepository,
            PostService postService
    ) {
        this.postLikeRepository = postLikeRepository;
        this.postService = postService;
    }

    @Override
    public PostLikeDto getById(UUID id) {
        return getById(id, PostLikeDto.class);
    }

    @Override
    public <R> R getById(UUID id, Class<R> classType) {
        PostLike like = postLikeRepository.getById(id);
        return ObjectData.mapTo(like, classType);
    }

    @Override
    public PostLikeDto create(CreatePostLikeCommand command) {
        User user = Context.getUser();

        if (user == null) throw new UserNotFoundException("Current user not found");

        Post post = postService.getById(command.getPostId(), Post.class);

        PostLike like = new PostLike();
        like.setPost(post);
        like.setUser(user);

        like = postLikeRepository.save(like);
        return ObjectData.mapTo(like, PostLikeDto.class);
    }

    @Override
    public PostLikeDto update(UUID id, CreatePostLikeCommand command) {
        return null;
    }

    @Override
    public void delete(UUID id, boolean force) {
        if (force) postLikeRepository.deleteById(id);
    }

    @Override
    public Page<PostLikeDto> getPageable(PostLikeFilter filter, Pageable pageable) {
        return null;
    }

    @Override
    public List<PostLikeDto> getList(PostLikeFilter filter) {
        return null;
    }

    @Override
    public void unlikePost(UUID postId) {
        User user = Context.getUser();

        if (user == null) throw new UserNotFoundException("Current user not found");

        Post post = postService.getById(postId, Post.class);

        PostLike like = postLikeRepository.findByUserAndPost(user, post);

        if (Objects.nonNull(like)) {
            postLikeRepository.deleteById(like.getId());
        }
    }
}
