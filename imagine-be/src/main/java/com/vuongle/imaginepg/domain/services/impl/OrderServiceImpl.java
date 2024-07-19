package com.vuongle.imaginepg.domain.services.impl;

import com.vuongle.imaginepg.application.commands.CreateBookOrderDetailCommand;
import com.vuongle.imaginepg.application.commands.CreateOrderCommand;
import com.vuongle.imaginepg.application.commands.LoanBookCommand;
import com.vuongle.imaginepg.application.dto.store.BookOrderDetailDto;
import com.vuongle.imaginepg.application.dto.store.BookOrderDto;
import com.vuongle.imaginepg.application.dto.store.OrderDto;
import com.vuongle.imaginepg.application.queries.OrderFilter;
import com.vuongle.imaginepg.domain.bpmn.BpmnBookProcessor;
import com.vuongle.imaginepg.domain.entities.User;
import com.vuongle.imaginepg.domain.entities.store.Book;
import com.vuongle.imaginepg.domain.entities.store.BookOrderDetail;
import com.vuongle.imaginepg.domain.entities.store.Discount;
import com.vuongle.imaginepg.domain.entities.store.Order;
import com.vuongle.imaginepg.domain.repositories.BaseQueryRepository;
import com.vuongle.imaginepg.domain.repositories.BaseRepository;
import com.vuongle.imaginepg.domain.services.*;
import com.vuongle.imaginepg.shared.utils.Context;
import com.vuongle.imaginepg.shared.utils.ObjectData;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final BaseQueryRepository<Order> orderQueryRepository;
    private final BaseRepository<Order> orderRepository;
    private final DiscountService discountService;
    private final UserService userService;
    private final BookService bookService;
    private final BpmnBookProcessor bookProcessor;

    private final BaseRepository<BookOrderDetail> bookOrderDetailRepository;
    private final BaseQueryRepository<BookOrderDetail> bookOrderDetailQueryRepository;

    public OrderServiceImpl(
            BaseQueryRepository<Order> orderQueryRepository,
            BaseRepository<Order> orderRepository,
            DiscountService discountService,
            UserService userService, BookService bookService, BpmnBookProcessor bookProcessor,
            BaseRepository<BookOrderDetail> bookOrderDetailRepository,
            BaseQueryRepository<BookOrderDetail> bookOrderDetailQueryRepository
    ) {
        this.orderQueryRepository = orderQueryRepository;
        this.orderRepository = orderRepository;
        this.discountService = discountService;
        this.userService = userService;
        this.bookService = bookService;
        this.bookProcessor = bookProcessor;
        this.bookOrderDetailRepository = bookOrderDetailRepository;
        this.bookOrderDetailQueryRepository = bookOrderDetailQueryRepository;
    }

    @Override
    public OrderDto getById(UUID id) {
        return ObjectData.mapTo(orderQueryRepository.getById(id), OrderDto.class);
    }

    @Override
    public <R> R getById(UUID id, Class<R> classType) {
        return null;
    }

    @Override
    public Page<OrderDto> getPageable(OrderFilter filter, Pageable pageable) {
        return null;
    }

    @Override
    public List<OrderDto> getList(OrderFilter filter) {
        return List.of();
    }

    @Override
    public OrderDto create(CreateOrderCommand command) {

        Order order = new Order();

        if (Objects.nonNull(command.getDiscountId())) {
            Discount discount = discountService.getById(command.getDiscountId(), Discount.class);
            order.setDiscount(discount);
        }

        User currentUser = Context.getUser();
        User user = userService.getById(currentUser.getId(), User.class);
        order.setCustomer(user);
        order.setState(Order.OrderState.INIT);
        order.setType(command.getType());
        order.setId(UUID.randomUUID());

        ProcessInstance instance = bookProcessor.loanBook(command);
        order.setProcessInstanceId(instance.getProcessInstanceId());
        order = orderRepository.save(order);

        for (BookOrderDto orderInfo: command.getBookOrderInfo()) {

            CreateBookOrderDetailCommand orderDetailCommand = CreateBookOrderDetailCommand.builder()
                    .orderId(order.getId())
                    .bookId(orderInfo.getBookId())
                    .amount(orderInfo.getAmount()).build();

            createOrderDetail(orderDetailCommand);
        }

        return ObjectData.mapTo(order, OrderDto.class);
    }

    @Override
    public OrderDto update(UUID id, CreateOrderCommand command) {
        return null;
    }

    @Override
    public void delete(UUID id, boolean force) {

    }


    private BookOrderDetailDto createOrderDetail(CreateBookOrderDetailCommand command) {

        Book book = bookService.getById(command.getBookId(), Book.class);
        Order order = orderQueryRepository.getById(command.getOrderId());

        BookOrderDetail orderDetail = new BookOrderDetail();
        orderDetail.setOrder(order);
        orderDetail.setBook(book);
        orderDetail.setAmount(command.getAmount());
        orderDetail.setId(UUID.randomUUID());

        return ObjectData.mapTo(bookOrderDetailRepository.save(orderDetail), BookOrderDetailDto.class);
    }

    @Override
    public void loanBook(LoanBookCommand command) {
        // verify loan command
        if (command.getOrderInfo().isEmpty()) {
            throw new IllegalArgumentException("Loan book info cannot be empty");
        }

        // create order with INIT STATE

    }

    @Override
    public void returnBook(LoanBookCommand command) {

    }

    @Override
    public void approveBookLoan() {

    }

    @Override
    public void requestOrderBook() {

    }
}
