package com.vuongle.imaginepg.domain.services;

import com.vuongle.imaginepg.application.commands.CreateBookAuthorCommand;
import com.vuongle.imaginepg.application.dto.store.BookAuthorDto;
import com.vuongle.imaginepg.application.queries.BookAuthorFilter;

public interface BookAuthorService extends
        BaseService<BookAuthorDto, CreateBookAuthorCommand>,
        BaseQueryService<BookAuthorDto, BookAuthorFilter> {
}
