package com.vuongle.imaginepg.interfaces.rest.v1;

import com.vuongle.imaginepg.application.commands.CreateOrderCommand;
import com.vuongle.imaginepg.application.dto.store.OrderDto;
import com.vuongle.imaginepg.domain.services.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrder(@PathVariable UUID id) {
        OrderDto order = orderService.getById(id);

        return ResponseEntity.ok(order);
    }

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(
            @RequestBody CreateOrderCommand command
    ) {
            OrderDto order = orderService.create(command);

            return ResponseEntity.ok(order);
    }


}
