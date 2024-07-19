package com.vuongle.imaginepg.domain.repositories;

import java.util.UUID;

public interface BaseRepository<T> {

    T save(T category);

    void deleteById(UUID id);
}