package com.vuongle.imaginepg.application.queries;

import com.vuongle.imaginepg.domain.entities.store.Discount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscountFilter implements Serializable {

    private String likeName;

    private long valueFrom;

    private Discount.DiscountUnit unit;
}
