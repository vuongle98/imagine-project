package com.vuongle.imaginepg.domain.services.impl;

import com.vuongle.imaginepg.application.commands.CreateCommentCommand;
import com.vuongle.imaginepg.application.dto.CommentDto;
import com.vuongle.imaginepg.application.exceptions.NoPermissionException;
import com.vuongle.imaginepg.application.queries.CommentFilter;
import com.vuongle.imaginepg.domain.entities.Comment;
import com.vuongle.imaginepg.domain.entities.Post;
import com.vuongle.imaginepg.domain.repositories.CommentRepository;
import com.vuongle.imaginepg.domain.repositories.PostRepository;
import com.vuongle.imaginepg.domain.services.CommentService;
import com.vuongle.imaginepg.infrastructure.specification.CommentSpecifications;
import com.vuongle.imaginepg.shared.utils.Context;
import com.vuongle.imaginepg.shared.utils.ObjectData;
import com.vuongle.imaginepg.shared.utils.ValidateResource;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Transactional
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final PostRepository postRepository;

    public CommentServiceImpl(
            CommentRepository commentRepository,
            PostRepository postRepository
    ) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @Override
    public CommentDto getById(UUID id) {
        return ObjectData.mapTo(commentRepository.getById(id), CommentDto.class);
    }

    @Override
    public <R> R getById(UUID id, Class<R> classType) {
        Comment comment = commentRepository.getById(id);

        // check permission
        if (!Context.hasModifyPermission() && !ValidateResource.isOwnResource(comment, Comment.class)) {
            throw new NoPermissionException("No permission");
        }

        return ObjectData.mapTo(comment, classType);
    }

    @Override
    public CommentDto create(CreateCommentCommand command) {

        Comment comment = ObjectData.mapTo(command, Comment.class);

        Post post = postRepository.getById(command.getPostId());

        comment.setPost(post);

        if (Objects.nonNull(command.getParentId())) {
            Comment parent = commentRepository.getById(command.getParentId());
            comment.setParent(parent);
        }

        comment.setUser(Context.getUser());

        comment = commentRepository.save(comment);

        return ObjectData.mapTo(comment, CommentDto.class);
    }

    @Override
    public CommentDto update(UUID id, CreateCommentCommand command) {

        Comment existedComment = getById(id, Comment.class);

        if (Objects.nonNull(command.getContent())) {
            existedComment.setContent(command.getContent());
        }

        existedComment = commentRepository.save(existedComment);
        return ObjectData.mapTo(existedComment, CommentDto.class);
    }

    @Override
    public void delete(UUID id, boolean force) {

        Comment comment = getById(id, Comment.class);

        if (force) {
            commentRepository.deleteById(id);
            return;
        }

        comment.setDeletedAt(Instant.now());
        commentRepository.save(comment);
    }

    @Override
    public Page<CommentDto> getPageable(CommentFilter filter, Pageable pageable) {
        Specification<Comment> specification = CommentSpecifications.withFilter(filter);
        Page<Comment> commentPage = commentRepository.findAll(specification, pageable);

        return commentPage.map(c -> ObjectData.mapTo(c, CommentDto.class));
    }

    @Override
    public List<CommentDto> getList(CommentFilter filter) {
        Specification<Comment> specification = CommentSpecifications.withFilter(filter);
        return ObjectData.mapListTo(commentRepository.findAll(specification), CommentDto.class);
    }
}
