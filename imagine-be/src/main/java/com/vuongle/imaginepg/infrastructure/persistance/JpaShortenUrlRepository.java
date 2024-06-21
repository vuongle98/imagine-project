package com.vuongle.imaginepg.infrastructure.persistance;

import com.vuongle.imaginepg.domain.entities.ShortenUrl;
import com.vuongle.imaginepg.domain.repositories.BaseRepository;
import com.vuongle.imaginepg.domain.repositories.ShortenUrlRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaShortenUrlRepository extends
  JpaRepository<ShortenUrl, UUID>,
  BaseRepository<ShortenUrl>,
  ShortenUrlRepository {
}
