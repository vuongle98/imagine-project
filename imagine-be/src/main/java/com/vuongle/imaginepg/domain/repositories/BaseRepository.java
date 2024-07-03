package com.vuongle.imaginepg.domain.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.UUID;

public interface BaseRepository<T> {

    T save(T category);

    T getById(UUID id);

    void deleteById(UUID id);

    List<T> findAll(Specification<T> spec);

    Page<T> findAll(Specification<T> spec, Pageable pageable);

//    List<T> saveAllAndFlush(List<T> data);

//    List<T> saveAll(Iterable<T> post);

}
