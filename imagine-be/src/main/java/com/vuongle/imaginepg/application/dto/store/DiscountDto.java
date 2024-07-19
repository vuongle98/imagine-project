package com.vuongle.imaginepg.application.dto.store;

import com.vuongle.imaginepg.domain.entities.store.Discount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscountDto implements Serializable {

    private UUID id;

    private String name;

    private long value;

    private Discount.DiscountUnit unit;

}
