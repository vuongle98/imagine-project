package com.vuongle.imaginepg.domain.services.impl;

import com.vuongle.imaginepg.application.commands.CreateBookAuthorCommand;
import com.vuongle.imaginepg.application.dto.store.BookAuthorDto;
import com.vuongle.imaginepg.application.queries.BookAuthorFilter;
import com.vuongle.imaginepg.domain.entities.store.BookAuthor;
import com.vuongle.imaginepg.domain.repositories.BaseQueryRepository;
import com.vuongle.imaginepg.domain.repositories.BaseRepository;
import com.vuongle.imaginepg.domain.services.BookAuthorService;
import com.vuongle.imaginepg.shared.utils.ObjectData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BookAuthorServiceImpl implements BookAuthorService {


    private final BaseRepository<BookAuthor> bookAuthorRepository;
    private final BaseQueryRepository<BookAuthor> bookAuthorQueryRepository;

    public BookAuthorServiceImpl(
            BaseRepository<BookAuthor> bookAuthorRepository,
            BaseQueryRepository<BookAuthor> bookAuthorQueryRepository
    ) {
        this.bookAuthorRepository = bookAuthorRepository;
        this.bookAuthorQueryRepository = bookAuthorQueryRepository;
    }

    @Override
    public BookAuthorDto getById(UUID id) {
        return ObjectData.mapTo(bookAuthorQueryRepository.getById(id), BookAuthorDto.class);
    }

    @Override
    public <R> R getById(UUID id, Class<R> classType) {
        return ObjectData.mapTo(bookAuthorQueryRepository.getById(id), classType);
    }

    @Override
    public Page<BookAuthorDto> getPageable(BookAuthorFilter filter, Pageable pageable) {
        return null;
    }

    @Override
    public List<BookAuthorDto> getList(BookAuthorFilter filter) {
        return List.of();
    }

    @Override
    public BookAuthorDto create(CreateBookAuthorCommand command) {
        return null;
    }

    @Override
    public BookAuthorDto update(UUID id, CreateBookAuthorCommand command) {
        return null;
    }

    @Override
    public void delete(UUID id, boolean force) {

    }
}
