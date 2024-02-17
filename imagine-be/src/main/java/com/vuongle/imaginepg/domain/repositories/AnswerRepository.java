package com.vuongle.imaginepg.domain.repositories;

import com.vuongle.imaginepg.domain.entities.Answer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.UUID;

public interface AnswerRepository {

    Answer save(Answer category);

    Answer getById(UUID id);

    void deleteById(UUID id);

    List<Answer> findAll(Specification<Answer> spec);

    Page<Answer> findAll(Specification<Answer> spec, Pageable pageable);

}
