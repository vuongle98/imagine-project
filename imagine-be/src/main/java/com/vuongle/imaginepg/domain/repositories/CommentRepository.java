package com.vuongle.imaginepg.domain.repositories;

import com.vuongle.imaginepg.domain.entities.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.UUID;

public interface CommentRepository {

    Comment getById(UUID id);

    List<Comment> findAll(Specification<Comment> spec);

    Page<Comment> findAll(Specification<Comment> spec, Pageable pageable);

    Comment save(Comment post);

    void deleteById(UUID id);
}
