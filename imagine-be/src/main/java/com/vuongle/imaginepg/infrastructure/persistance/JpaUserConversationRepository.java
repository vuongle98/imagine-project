package com.vuongle.imaginepg.infrastructure.persistance;

import com.vuongle.imaginepg.domain.entities.Conversation;
import com.vuongle.imaginepg.domain.entities.UserConversation;
import com.vuongle.imaginepg.domain.repositories.BaseRepository;
import com.vuongle.imaginepg.domain.repositories.ConversationRepository;
import com.vuongle.imaginepg.domain.repositories.UserConversationRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaUserConversationRepository extends JpaRepository<UserConversation, UUID>, BaseRepository<UserConversation>, UserConversationRepository {
}
