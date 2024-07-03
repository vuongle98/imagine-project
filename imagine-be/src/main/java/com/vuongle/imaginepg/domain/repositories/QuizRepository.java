package com.vuongle.imaginepg.domain.repositories;

import com.vuongle.imaginepg.domain.entities.Quiz;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.UUID;

public interface QuizRepository {

    Quiz save(Quiz category);

    Quiz getById(UUID id);

    void deleteById(UUID id);

    List<Quiz> findAll(Specification<Quiz> spec);

    Page<Quiz> findAll(Specification<Quiz> spec, Pageable pageable);

}
