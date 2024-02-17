package com.vuongle.imaginepg.domain.repositories;

import com.vuongle.imaginepg.domain.entities.Conversation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.UUID;

public interface ConversationRepository {

    Conversation save(Conversation category);

    Conversation getById(UUID id);

    void deleteById(UUID id);

    List<Conversation> findAll(Specification<Conversation> spec);

    Page<Conversation> findAll(Specification<Conversation> spec, Pageable pageable);
}
