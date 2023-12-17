package com.vuongle.imaginepg.domain.repositories;

import com.vuongle.imaginepg.domain.entities.Post;
import com.vuongle.imaginepg.domain.entities.PostLike;
import com.vuongle.imaginepg.domain.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.UUID;

public interface PostLikeRepository {

    PostLike getById(UUID id);

    List<PostLike> findAll();

    List<PostLike> findAll(Specification<PostLike> spec);

    Page<PostLike> findAll(Specification<PostLike> spec, Pageable pageable);

    PostLike save(PostLike post);

    void deleteById(UUID id);

    PostLike findByUserAndPost(User user, Post post);

}
