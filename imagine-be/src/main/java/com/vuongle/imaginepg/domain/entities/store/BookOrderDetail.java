package com.vuongle.imaginepg.domain.entities.store;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Data
@Getter
@Setter
@Entity
@Table(name = "book_order_detail")
public class BookOrderDetail implements Serializable {

    @Id
    private UUID id;

    @JoinColumn(name = "order_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;

    @JoinColumn(name = "book_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Book book;

    private int amount;

}
