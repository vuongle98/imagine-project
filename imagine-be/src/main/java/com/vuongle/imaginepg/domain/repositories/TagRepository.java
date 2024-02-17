package com.vuongle.imaginepg.domain.repositories;

import com.vuongle.imaginepg.domain.entities.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.UUID;

public interface TagRepository {

    Tag save(Tag category);

    Tag getById(UUID id);

    void deleteById(UUID id);

    List<Tag> findAll(Specification<Tag> spec);

    Page<Tag> findAll(Specification<Tag> spec, Pageable pageable);
    
}
