package com.vuongle.imaginepg.application.commands;

import com.vuongle.imaginepg.domain.entities.store.Discount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateDiscountCommand implements Serializable {

    private String name;

    private long value;

    private Discount.DiscountUnit unit;

}
