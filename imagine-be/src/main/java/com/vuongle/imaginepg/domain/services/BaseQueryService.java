package com.vuongle.imaginepg.domain.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BaseQueryService<T, F> {

//    Optional<T> findById(UUID id);
//
//    <R> Optional<R> findById(UUID id, Class<R> classType);

    T getById(UUID id);

    <R> R getById(UUID id, Class<R> classType);

    Page<T> getPageable(F filter, Pageable pageable);

    List<T> getList(F filter);
}
