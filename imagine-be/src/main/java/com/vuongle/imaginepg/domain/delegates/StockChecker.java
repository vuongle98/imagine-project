package com.vuongle.imaginepg.domain.delegates;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vuongle.imaginepg.application.dto.store.BookCheckerResult;
import com.vuongle.imaginepg.application.dto.store.BookOrderDto;
import com.vuongle.imaginepg.domain.services.BookService;
import com.vuongle.imaginepg.shared.utils.ObjectData;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Component
public class StockChecker implements JavaDelegate {

    private final BookService bookService;

    public StockChecker(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        List<BookOrderDto> orderDto = new ObjectMapper().convertValue(delegateExecution.getVariable("bookLoanInfo"), new TypeReference<>() {});

        BookCheckerResult checkerResult = bookService.checkBookAvailable(orderDto);

        delegateExecution.setVariable("available", checkerResult.getOutOfStocks().isEmpty());
        delegateExecution.setVariable("outOfStocks", checkerResult.getOutOfStocks());
    }
}
