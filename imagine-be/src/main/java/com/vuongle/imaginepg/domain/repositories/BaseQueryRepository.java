package com.vuongle.imaginepg.domain.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BaseQueryRepository<T> {

    T getById(UUID id);

    Optional<T> findById(UUID id);

    List<T> findAll(Specification<T> spec);

    Page<T> findAll(Specification<T> spec, Pageable pageable);

    long count(Specification<T> spec);

//    List<T> saveAllAndFlush(List<T> data);

//    List<T> saveAll(Iterable<T> post);

}
