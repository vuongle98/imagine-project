package com.vuongle.imaginepg.domain.repositories;

import com.vuongle.imaginepg.domain.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    User save(User user);

    User getById(UUID id);

    List<User> findAll();

    List<User> findAll(Specification<User> spec);

    Page<User> findAll(Specification<User> spec, Pageable pageable);

    void deleteById(UUID id);
}
