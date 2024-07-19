package com.vuongle.imaginepg.domain.services;

import com.vuongle.imaginepg.application.commands.CreateBookCommand;
import com.vuongle.imaginepg.application.commands.LoanBookCommand;
import com.vuongle.imaginepg.application.dto.store.BookCheckerResult;
import com.vuongle.imaginepg.application.dto.store.BookDto;
import com.vuongle.imaginepg.application.dto.store.BookOrderDto;
import com.vuongle.imaginepg.application.queries.BookFilter;

import java.util.List;
import java.util.UUID;

public interface BookService extends
        BaseService<BookDto, CreateBookCommand>,
        BaseQueryService<BookDto, BookFilter> {

    BookCheckerResult checkBookAvailable(List<BookOrderDto> orderInfo);

}
