package com.vuongle.imaginepg.infrastructure.persistance;

import com.vuongle.imaginepg.domain.entities.PostLike;
import com.vuongle.imaginepg.domain.repositories.BaseQueryRepository;
import com.vuongle.imaginepg.domain.repositories.PostLikeRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaPostLikeRepository extends JpaRepository<PostLike, UUID>, BaseQueryRepository<PostLike>, PostLikeRepository {
}
