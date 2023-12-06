package com.vuongle.imaginepg.domain.repositories;

import com.vuongle.imaginepg.domain.entities.File;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.UUID;

public interface FileRepository {

    File getById(UUID id);

    List<File> findAll();

    List<File> findAll(Specification<File> spec);

    Page<File> findAll(Specification<File> spec, Pageable pageable);

    File save(File post);

    void deleteById(UUID id);
}
