package com.vuongle.imaginepg.domain.entities.store;

import com.vuongle.imaginepg.domain.entities.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@Getter
@Setter
@Table(name = "orders")
@Entity
public class Order implements Serializable {

    @Id
    private UUID id;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User customer;

    @JoinColumn(name = "discount_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Discount discount;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<BookOrderDetail> bookOrderDetails;

    @Enumerated(EnumType.STRING)
    private OrderState state;

    @Enumerated(EnumType.STRING)
    private OrderType type;

    private String processInstanceId;
    private String currentTaskKey;
    private String currentTaskId;

    private Instant orderAt = Instant.now();

    public enum OrderType {
        LOAN, BUY
    }

    public enum OrderState {
        INIT, VERIFY, PREPARE, SHIPPING, COMPLETED, CANCELLED, OUT_OF_STOCK
    }
}
