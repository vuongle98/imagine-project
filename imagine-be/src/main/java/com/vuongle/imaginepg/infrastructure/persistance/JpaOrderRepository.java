package com.vuongle.imaginepg.infrastructure.persistance;

import com.vuongle.imaginepg.domain.entities.store.Order;
import com.vuongle.imaginepg.domain.repositories.BaseQueryRepository;
import com.vuongle.imaginepg.domain.repositories.BaseRepository;
import com.vuongle.imaginepg.domain.repositories.OrderRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaOrderRepository extends BaseRepository<Order>,
        BaseQueryRepository<Order>, JpaRepository<Order, UUID>, OrderRepository {
}
