package com.vuongle.imaginepg.domain.services.impl;

import com.vuongle.imaginepg.application.commands.CreatePostCommand;
import com.vuongle.imaginepg.application.dto.PostDto;
import com.vuongle.imaginepg.application.queries.PostFilter;
import com.vuongle.imaginepg.domain.entities.Category;
import com.vuongle.imaginepg.domain.entities.Post;
import com.vuongle.imaginepg.domain.entities.User;
import com.vuongle.imaginepg.domain.repositories.CategoryRepository;
import com.vuongle.imaginepg.domain.repositories.PostRepository;
import com.vuongle.imaginepg.domain.services.PostService;
import com.vuongle.imaginepg.infrastructure.specification.PostSpecifications;
import com.vuongle.imaginepg.shared.utils.Context;
import com.vuongle.imaginepg.shared.utils.ObjectData;
import com.vuongle.imaginepg.shared.utils.Slugify;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    private final CategoryRepository categoryRepository;

    public PostServiceImpl(
            PostRepository postRepository,
            CategoryRepository categoryRepository
    ) {
        this.postRepository = postRepository;
        this.categoryRepository = categoryRepository;
    }


    @Override
    public PostDto getById(UUID id) {
        return ObjectData.mapTo(postRepository.getById(id), PostDto.class);
    }

    @Override
    public PostDto create(CreatePostCommand command) {
        Post post = ObjectData.mapTo(command, Post.class);

        post.setSlug(Slugify.toSlug(post.getTitle()));
        Category category = categoryRepository.getById(command.getCategoryId());
        post.setCategory(category);

        User user = Context.getUser();

        post.setCreator(user);

        post = postRepository.save(post);
        return ObjectData.mapTo(post, PostDto.class);
    }

    @Override
    public PostDto update(UUID id, CreatePostCommand command) {
        Post existedPost = postRepository.getById(id);

        if (Objects.nonNull(command.getCategoryId())) {
            Category category = categoryRepository.getById(command.getCategoryId());
            existedPost.setCategory(category);
        }

        if (Objects.nonNull(command.getTitle())) {
            existedPost.setTitle(command.getTitle());
        }

        if (Objects.nonNull(command.getContent())) {
            existedPost.setContent(command.getContent());
        }

        if (Objects.nonNull(command.getDescription())) {
            existedPost.setDescription(command.getDescription());
        }

        existedPost = postRepository.save(existedPost);

        return ObjectData.mapTo(existedPost, PostDto.class);
    }

    @Override
    public void delete(UUID id, boolean force) {
        if (force) {
            postRepository.deleteById(id);
            return;
        }

        Post existedPost = postRepository.getById(id);
        existedPost.setDeletedAt(Instant.now());
        postRepository.save(existedPost);

        // delete all comment
    }

    @Override
    public Page<PostDto> getAll(PostFilter filter, Pageable pageable) {
        Specification<Post> specification = PostSpecifications.withFilter(filter);
        Page<Post> postPage = postRepository.findAll(specification, pageable);
//        return postPage.map(post -> {
//
//            PostDto postDto = ObjectData.mapTo(post, PostDto.class);
//            postDto.setNumLikes(post.getLikeByUsers().size());
//            return postDto;
//        });

        return postPage.map(post -> ObjectData.mapTo(post, PostDto.class));
    }

    @Override
    public List<PostDto> getAll(PostFilter filter) {
        Specification<Post> specification = PostSpecifications.withFilter(filter);
        return ObjectData.mapListTo(postRepository.findAll(specification), PostDto.class);
    }
}
