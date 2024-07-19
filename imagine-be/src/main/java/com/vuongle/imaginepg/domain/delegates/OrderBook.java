package com.vuongle.imaginepg.domain.delegates;

import com.vuongle.imaginepg.domain.services.OrderService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
public class OrderBook implements JavaDelegate {

    private final OrderService orderService;

    public OrderBook(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

    }
}
