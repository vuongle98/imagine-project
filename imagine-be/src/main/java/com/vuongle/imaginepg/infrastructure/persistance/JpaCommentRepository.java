package com.vuongle.imaginepg.infrastructure.persistance;

import com.vuongle.imaginepg.domain.entities.Comment;
import com.vuongle.imaginepg.domain.repositories.CommentRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaCommentRepository extends JpaRepository<Comment, UUID>, CommentRepository {
}
