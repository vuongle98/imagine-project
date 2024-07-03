package com.vuongle.imaginepg.infrastructure.persistance;

import com.vuongle.imaginepg.domain.entities.Post;
import com.vuongle.imaginepg.domain.repositories.BaseRepository;
import com.vuongle.imaginepg.domain.repositories.PostRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface JpaPostRepository extends JpaRepository<Post, UUID>, BaseRepository<Post>, PostRepository {

    @Override
    default List<Post> saveAllPosts(Iterable<Post> post) {
        return saveAllAndFlush(post);
    }
}
