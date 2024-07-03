package com.vuongle.imaginepg.infrastructure.persistance;

import com.vuongle.imaginepg.domain.entities.ShortenUrl;
import com.vuongle.imaginepg.domain.repositories.BaseRepository;
import com.vuongle.imaginepg.domain.repositories.ShortenUrlRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaShortenUrlRepository extends
        JpaRepository<ShortenUrl, UUID>,
        BaseRepository<ShortenUrl>,
        ShortenUrlRepository {
}
