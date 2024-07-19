package com.vuongle.imaginepg.domain.services.impl;

import com.vuongle.imaginepg.application.commands.CreatePostCommand;
import com.vuongle.imaginepg.application.dto.PostDto;
import com.vuongle.imaginepg.application.exceptions.NoPermissionException;
import com.vuongle.imaginepg.application.queries.PostFilter;
import com.vuongle.imaginepg.domain.entities.Category;
import com.vuongle.imaginepg.domain.entities.File;
import com.vuongle.imaginepg.domain.entities.Post;
import com.vuongle.imaginepg.domain.repositories.BaseQueryRepository;
import com.vuongle.imaginepg.domain.repositories.PostRepository;
import com.vuongle.imaginepg.domain.services.FileService;
import com.vuongle.imaginepg.domain.services.PostService;
import com.vuongle.imaginepg.infrastructure.specification.PostSpecifications;
import com.vuongle.imaginepg.shared.utils.Context;
import com.vuongle.imaginepg.shared.utils.ObjectData;
import com.vuongle.imaginepg.shared.utils.Slugify;
import com.vuongle.imaginepg.shared.utils.ValidateResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@Transactional
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    private final FileService fileService;

    private final BaseQueryRepository<Category> categoryRepository;

    public PostServiceImpl(
            PostRepository postRepository, FileService fileService,
            BaseQueryRepository<Category> categoryRepository
    ) {
        this.postRepository = postRepository;
        this.fileService = fileService;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public PostDto getById(UUID id) {
        return getById(id, PostDto.class);
    }

    @Override
    public <R> R getById(UUID id, Class<R> classType) {

        Post post = postRepository.getById(id);

        // check permission

        if (Objects.isNull(post.getPublishedAt()) && !Context.hasModifyPermission() && !ValidateResource.isOwnResource(post, Post.class)) {
            throw new NoPermissionException("No permission");
        }

        return ObjectData.mapTo(post, classType);
    }

    @Override
    public PostDto create(CreatePostCommand command) {
        Post post = ObjectData.mapTo(command, Post.class);

        post.setSlug(Slugify.toSlug(post.getTitle()));
        Category category = categoryRepository.getById(command.getCategoryId());
        post.setCategory(category);

        if (Objects.nonNull(command.getFileId())) {
            File file = fileService.getById(command.getFileId(), File.class);
            post.setFile(file);
        }

        post.setCreator(Context.getUser());

        post = postRepository.save(post);
        return ObjectData.mapTo(post, PostDto.class);
    }

    @Override
    public PostDto update(UUID id, CreatePostCommand command) {
        Post existedPost = getById(id, Post.class);

        if (Objects.nonNull(command.getCategoryId())) {
            Category category = categoryRepository.getById(command.getCategoryId());
            existedPost.setCategory(category);
        }

        if (Objects.nonNull(command.getTitle())) {
            existedPost.setTitle(command.getTitle());
            existedPost.setSlug(Slugify.toSlug(existedPost.getTitle()));
        }

        if (Objects.nonNull(command.getContent())) {
            existedPost.setContent(command.getContent());
        }

        if (Objects.nonNull(command.getDescription())) {
            existedPost.setDescription(command.getDescription());
        }

        if (Objects.nonNull(command.getFeatured())) {
            existedPost.setFeatured(command.getFeatured());
        }

        if (Objects.nonNull(command.getPublish())) {
            existedPost.setPublishedAt(Instant.now());
        }

        existedPost = postRepository.save(existedPost);

        return ObjectData.mapTo(existedPost, PostDto.class);
    }

    @Override
    public void delete(UUID id, boolean force) {

        Post existedPost = getById(id, Post.class);

        if (force) {
            postRepository.deleteById(id);
            return;
        }

        existedPost.setDeletedAt(Instant.now());
        postRepository.save(existedPost);

        // delete all comment
    }

    @Override
    public Page<PostDto> getPageable(PostFilter filter, Pageable pageable) {
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
    public List<PostDto> getList(PostFilter filter) {
        Specification<Post> specification = PostSpecifications.withFilter(filter);
        return ObjectData.mapListTo(postRepository.findAll(specification), PostDto.class);
    }
}
