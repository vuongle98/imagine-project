package com.vuongle.imaginepg.domain.entities.store;

import com.vuongle.imaginepg.domain.entities.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
@Getter
@Setter
@Entity
@Table(name = "discount")
public class Discount implements Serializable {

    @Id
    private UUID id;

    private String name;

    private long value;

    @Enumerated(EnumType.STRING)
    private DiscountUnit unit;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "customer_discounts",
            joinColumns = @JoinColumn(name = "discount__id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> customers;

    @OneToMany(mappedBy = "discount", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Order> orders;

    public enum DiscountUnit {
        PRICE, PERCENT
    }
}
