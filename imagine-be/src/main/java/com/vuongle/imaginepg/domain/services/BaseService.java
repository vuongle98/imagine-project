package com.vuongle.imaginepg.domain.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface BaseService<T, C> {

    T create(C command);

    T update(UUID id, C command);

    void delete(UUID id, boolean force);
}
