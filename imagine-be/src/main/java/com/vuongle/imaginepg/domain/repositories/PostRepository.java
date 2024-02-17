package com.vuongle.imaginepg.domain.repositories;

import com.vuongle.imaginepg.domain.entities.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.UUID;

public interface PostRepository {

    Post getById(UUID id);

    List<Post> findAll();

    List<Post> findAll(Specification<Post> spec);

    Page<Post> findAll(Specification<Post> spec, Pageable pageable);

    Post save(Post post);

    List<Post> saveAllPosts(Iterable<Post> post);

    void deleteById(UUID id);

    void deleteByCategoryId(UUID categoryId);

}
