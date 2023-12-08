package com.vuongle.imaginepg.infrastructure.persistance;

import com.vuongle.imaginepg.domain.entities.ChatMessage;
import com.vuongle.imaginepg.domain.repositories.BaseRepository;
import com.vuongle.imaginepg.domain.repositories.ChatMessageRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaChatMessageRepository extends JpaRepository<ChatMessage, UUID>, BaseRepository<ChatMessage>, ChatMessageRepository {
}
