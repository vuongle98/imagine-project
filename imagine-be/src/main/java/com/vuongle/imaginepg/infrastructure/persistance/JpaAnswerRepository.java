package com.vuongle.imaginepg.infrastructure.persistance;

import com.vuongle.imaginepg.domain.entities.Answer;
import com.vuongle.imaginepg.domain.repositories.AnswerRepository;
import com.vuongle.imaginepg.domain.repositories.BaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaAnswerRepository extends JpaRepository<Answer, UUID>, BaseRepository<Answer>, AnswerRepository {
}
