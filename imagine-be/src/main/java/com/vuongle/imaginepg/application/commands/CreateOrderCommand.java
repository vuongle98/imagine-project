package com.vuongle.imaginepg.application.commands;

import com.vuongle.imaginepg.application.dto.store.BookOrderDto;
import com.vuongle.imaginepg.domain.entities.store.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderCommand implements Serializable {

//    private UUID customerId;

    private List<BookOrderDto> bookOrderInfo;

    @Nullable
    private UUID discountId;

    private Order.OrderType type;
}
