package com.vuongle.imaginepg.application.dto.store;

import com.vuongle.imaginepg.application.dto.UserDto;
import com.vuongle.imaginepg.domain.entities.User;
import com.vuongle.imaginepg.domain.entities.store.Discount;
import com.vuongle.imaginepg.domain.entities.store.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto implements Serializable {

    private UUID id;

    private UserDto customer;

    private DiscountDto discount;

    private Order.OrderState state;

    private List<BookOrderDetailDto> bookOrderDetails;
}
