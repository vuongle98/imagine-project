package com.vuongle.imaginepg.domain.repositories;

import com.vuongle.imaginepg.domain.entities.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.UUID;

public interface QuestionRepository {

    Question save(Question category);

    Question getById(UUID id);

    void deleteById(UUID id);

    List<Question> findAll(Specification<Question> spec);

    Page<Question> findAll(Specification<Question> spec, Pageable pageable);
    
}
