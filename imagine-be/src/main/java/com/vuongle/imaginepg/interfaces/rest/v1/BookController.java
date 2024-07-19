package com.vuongle.imaginepg.interfaces.rest.v1;

import com.vuongle.imaginepg.application.commands.CreateBookCommand;
import com.vuongle.imaginepg.application.commands.LoanBookCommand;
import com.vuongle.imaginepg.application.dto.store.BookDto;
import com.vuongle.imaginepg.application.queries.BookFilter;
import com.vuongle.imaginepg.domain.services.BookService;
import com.vuongle.imaginepg.domain.services.OrderService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/book")
public class BookController {
    
    private final BookService bookService;
    private final OrderService orderService;

    public BookController(BookService bookService, OrderService orderService) {
        this.bookService = bookService;
        this.orderService = orderService;
    }

    @GetMapping
    @SecurityRequirement(
            name = "Bearer authentication"
    )
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN', 'MODERATOR')")
    public ResponseEntity<Page<BookDto>> search(
            BookFilter filter,
            Pageable pageable
    ) {
        Page<BookDto> bookPageable = bookService.getPageable(filter, pageable);

        return ResponseEntity.ok(bookPageable);
    }

    @PostMapping
    @SecurityRequirement(
            name = "Bearer authentication"
    )
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN', 'MODERATOR')")
    public ResponseEntity<BookDto> create(
            @RequestBody CreateBookCommand command
    ) {
        BookDto book = bookService.create(command);

        return ResponseEntity.ok(book);
    }

    @PutMapping("/{id}")
    @SecurityRequirement(
            name = "Bearer authentication"
    )
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN', 'MODERATOR')")
    public ResponseEntity<BookDto> update(
            @PathVariable(value = "id") UUID id,
            @RequestBody CreateBookCommand command
    ) {
        BookDto updatedBook = bookService.update(id, command);

        return ResponseEntity.ok(updatedBook);
    }

    @GetMapping("/{id}")
    @SecurityRequirement(
            name = "Bearer authentication"
    )
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN', 'MODERATOR')")
    public ResponseEntity<BookDto> getById(
            @PathVariable(value = "id") UUID id
    ) {
        BookDto book = bookService.getById(id);

        return ResponseEntity.ok(book);
    }

    @PostMapping("/{id}/loan")
    @SecurityRequirement(
            name = "Bearer authentication"
    )
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN', 'MODERATOR')")
    public ResponseEntity<BookDto> loanBook(
            @RequestBody LoanBookCommand command
            ) {
        orderService.loanBook(command);

        return ResponseEntity.ok().build();
    }

}
