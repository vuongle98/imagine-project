package com.vuongle.imaginepg.domain.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface BaseService<T, C, F> {

    T getById(UUID id);

    <R> R getById(UUID id, Class<R> classType);

    T create(C command);

    T update(UUID id, C command);

    void delete(UUID id, boolean force);

    Page<T> getAll(F filter, Pageable pageable);

    List<T> getAll(F filter);
}
