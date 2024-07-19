package com.vuongle.imaginepg.infrastructure.persistance;

import com.vuongle.imaginepg.domain.entities.store.BookOrderDetail;
import com.vuongle.imaginepg.domain.repositories.BaseQueryRepository;
import com.vuongle.imaginepg.domain.repositories.BaseRepository;
import com.vuongle.imaginepg.domain.repositories.BookOrderDetailRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaBookOrderDetailRepository
    extends BaseRepository<BookOrderDetail>,
        BaseQueryRepository<BookOrderDetail>,
        JpaRepository<BookOrderDetail, UUID>,
        BookOrderDetailRepository {
}
