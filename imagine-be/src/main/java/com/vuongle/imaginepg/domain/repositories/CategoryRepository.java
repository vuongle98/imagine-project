package com.vuongle.imaginepg.domain.repositories;

import com.vuongle.imaginepg.domain.entities.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.UUID;

public interface CategoryRepository {

    Category save(Category category);

    Category getById(UUID id);

    void deleteById(UUID id);

    List<Category> findAll(Specification<Category> spec);

    Page<Category> findAll(Specification<Category> spec, Pageable pageable);
}
