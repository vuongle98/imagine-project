package com.vuongle.imaginepg.domain.repositories;

import com.vuongle.imaginepg.domain.entities.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.UUID;

public interface TaskRepository {

    Task getById(UUID id);

    List<Task> findAll();

    List<Task> findAll(Specification<Task> spec);

    Page<Task> findAll(Specification<Task> spec, Pageable pageable);

    Task save(Task post);

    void deleteById(UUID id);
}
