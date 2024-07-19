package com.vuongle.imaginepg.domain.services;

import com.vuongle.imaginepg.application.commands.CreateOrderCommand;
import com.vuongle.imaginepg.application.commands.LoanBookCommand;
import com.vuongle.imaginepg.application.dto.store.OrderDto;
import com.vuongle.imaginepg.application.queries.OrderFilter;

public interface OrderService extends
        BaseService<OrderDto, CreateOrderCommand>,
        BaseQueryService<OrderDto, OrderFilter> {

    void loanBook(LoanBookCommand command);

    void returnBook(LoanBookCommand command);

    void approveBookLoan();
    void requestOrderBook();

}
