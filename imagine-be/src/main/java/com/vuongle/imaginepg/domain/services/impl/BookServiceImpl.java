package com.vuongle.imaginepg.domain.services.impl;

import com.vuongle.imaginepg.application.commands.CreateBookCommand;
import com.vuongle.imaginepg.application.commands.LoanBookCommand;
import com.vuongle.imaginepg.application.dto.store.BookCheckerResult;
import com.vuongle.imaginepg.application.dto.store.BookDto;
import com.vuongle.imaginepg.application.dto.store.BookOrderDto;
import com.vuongle.imaginepg.application.queries.BookFilter;
import com.vuongle.imaginepg.domain.bpmn.BpmnBookProcessor;
import com.vuongle.imaginepg.domain.entities.store.Book;
import com.vuongle.imaginepg.domain.entities.store.BookAuthor;
import com.vuongle.imaginepg.domain.repositories.BaseQueryRepository;
import com.vuongle.imaginepg.domain.repositories.BaseRepository;
import com.vuongle.imaginepg.domain.services.BookService;
import com.vuongle.imaginepg.infrastructure.specification.BookSpecifications;
import com.vuongle.imaginepg.shared.utils.ObjectData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class BookServiceImpl implements BookService {


    private final BaseRepository<Book> bookRepository;
    private final BaseQueryRepository<Book> bookQueryRepository;

    private final BaseQueryRepository<BookAuthor> bookAuthorQueryRepository;

    public BookServiceImpl(BaseRepository<Book> bookRepository,
                           BaseQueryRepository<Book> bookQueryRepository,
                           BaseQueryRepository<BookAuthor> bookAuthorQueryRepository) {
        this.bookRepository = bookRepository;
        this.bookQueryRepository = bookQueryRepository;
        this.bookAuthorQueryRepository = bookAuthorQueryRepository;
    }

    @Override
    public BookDto getById(UUID id) {
        return ObjectData.mapTo(bookQueryRepository.getById(id), BookDto.class);
    }

    @Override
    public <R> R getById(UUID id, Class<R> classType) {
        return ObjectData.mapTo(getById(id), classType);
    }

    @Override
    public Page<BookDto> getPageable(BookFilter filter, Pageable pageable) {
        Specification<Book> specification = BookSpecifications.withFilter(filter);
        Page<Book> commentPage = bookQueryRepository.findAll(specification, pageable);

        return commentPage.map(c -> ObjectData.mapTo(c, BookDto.class));
    }

    @Override
    public List<BookDto> getList(BookFilter filter) {
        return List.of();
    }

    @Override
    public BookDto create(CreateBookCommand command) {

        Book book = ObjectData.mapTo(command, Book.class);

        BookAuthor author = bookAuthorQueryRepository.getById(command.getAuthorId());
        book.setAuthor(author);

        book = bookRepository.save(book);
        return ObjectData.mapTo(book, BookDto.class);
    }

    @Override
    public BookDto update(UUID id, CreateBookCommand command) {

        Book book = bookQueryRepository.getById(id);

        BookAuthor author = bookAuthorQueryRepository.getById(command.getAuthorId());
        book.setAuthor(author);

        book.setTitle(command.getTitle());
        book.setDescription(command.getDescription());
        book.setEdition(command.getEdition());
        book.setPublishedAt(command.getPublishedAt());
        book.setPrice(command.getPrice());

        return ObjectData.mapTo(bookRepository.save(book), BookDto.class);
    }

    public BookDto patch(UUID id, CreateBookCommand command) {
        Book book = bookQueryRepository.getById(id);

        if (Objects.nonNull(command.getAuthorId())) {
            BookAuthor author = bookAuthorQueryRepository.getById(command.getAuthorId());
            book.setAuthor(author);
        }

        if (command.getPrice() > 0) {
            book.setPrice(command.getPrice());
        }

        if (Objects.nonNull(command.getTitle())) {
            book.setTitle(command.getTitle());
        }

        if (Objects.nonNull(command.getDescription())) {
            book.setDescription(command.getDescription());
        }

        if (command.getEdition() > 0) {
            book.setEdition(command.getEdition());
        }

        if (Objects.nonNull(command.getPublishedAt())) {
            book.setPublishedAt(command.getPublishedAt());
        }

        return ObjectData.mapTo(bookRepository.save(book), BookDto.class);
    }

    @Override
    public void delete(UUID id, boolean force) {
        bookRepository.deleteById(id);
    }

    @Override
    public BookCheckerResult checkBookAvailable(List<BookOrderDto> orderInfo) {

        BookCheckerResult result = new BookCheckerResult();

        List<BookDto> outOfStocks = new ArrayList<>();

        for (BookOrderDto order : orderInfo) {
            BookDto bookInfo = getById(order.getBookId());
            if (bookInfo.getAvailability() <= order.getAmount()) {
                outOfStocks.add(bookInfo);
            }
        }

        result.setOutOfStocks(outOfStocks);
        return result;
    }
}
