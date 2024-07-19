package com.vuongle.imaginepg.domain.entities.store;

import com.vuongle.imaginepg.domain.entities.User;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "book")
public class Book implements Serializable {

    @Id
    private UUID id;

    private String title;

    private String description;

    private Instant publishedAt;

    private long price;

    private int edition;

    private long availability;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User publisher;

    @JoinColumn(name = "author_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private BookAuthor author;

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<BookOrderDetail> bookOrderDetails;

//    @ManyToMany
//    @JoinTable(name = "book_order_detail",
//            joinColumns = @JoinColumn(name = "book_id"),
//            inverseJoinColumns = @JoinColumn(name = "order_id"))
//    private List<Order> orders;

}
