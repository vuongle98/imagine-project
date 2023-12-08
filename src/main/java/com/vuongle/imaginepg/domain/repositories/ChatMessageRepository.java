package com.vuongle.imaginepg.domain.repositories;

import com.vuongle.imaginepg.domain.entities.ChatMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.UUID;

public interface ChatMessageRepository {
    ChatMessage save(ChatMessage category);

    ChatMessage getById(UUID id);

    void deleteById(UUID id);

    List<ChatMessage> findAll(Specification<ChatMessage> spec);

    Page<ChatMessage> findAll(Specification<ChatMessage> spec, Pageable pageable);
}
