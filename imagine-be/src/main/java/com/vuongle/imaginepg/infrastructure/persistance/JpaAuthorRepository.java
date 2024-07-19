package com.vuongle.imaginepg.infrastructure.persistance;

import com.vuongle.imaginepg.domain.entities.store.BookAuthor;
import com.vuongle.imaginepg.domain.repositories.BaseQueryRepository;
import com.vuongle.imaginepg.domain.repositories.BaseRepository;
import com.vuongle.imaginepg.domain.repositories.BookAuthorRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

public interface JpaAuthorRepository
        extends BaseRepository<BookAuthor>,
        BaseQueryRepository<BookAuthor>,
        JpaRepository<BookAuthor, UUID>,
        BookAuthorRepository {
}
