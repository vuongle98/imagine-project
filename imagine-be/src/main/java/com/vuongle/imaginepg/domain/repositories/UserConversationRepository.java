package com.vuongle.imaginepg.domain.repositories;

import com.vuongle.imaginepg.domain.entities.Category;
import com.vuongle.imaginepg.domain.entities.UserConversation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.UUID;

public interface UserConversationRepository {

    UserConversation save(UserConversation category);

    UserConversation getById(UUID id);

    void deleteById(UUID id);

    List<UserConversation> findAll(Specification<UserConversation> spec);

    Page<UserConversation> findAll(Specification<UserConversation> spec, Pageable pageable);
}
