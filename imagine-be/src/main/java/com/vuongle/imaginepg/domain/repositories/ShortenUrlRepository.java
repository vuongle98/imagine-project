package com.vuongle.imaginepg.domain.repositories;

import com.vuongle.imaginepg.domain.entities.ShortenUrl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.UUID;

public interface ShortenUrlRepository {

    ShortenUrl save(ShortenUrl category);

    ShortenUrl getById(UUID id);

    void deleteById(UUID id);

    List<ShortenUrl> findAll(Specification<ShortenUrl> spec);

    Page<ShortenUrl> findAll(Specification<ShortenUrl> spec, Pageable pageable);
}
