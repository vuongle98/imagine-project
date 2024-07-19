package com.vuongle.imaginepg.interfaces.rest.v1;

import com.vuongle.imaginepg.application.commands.CreateBookAuthorCommand;
import com.vuongle.imaginepg.application.dto.store.BookAuthorDto;
import com.vuongle.imaginepg.application.queries.BookAuthorFilter;
import com.vuongle.imaginepg.domain.services.BookAuthorService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/book-author")
public class BookAuthorController {
    
    
    private final BookAuthorService bookAuthorService;

    public BookAuthorController(BookAuthorService bookAuthorService) {
        this.bookAuthorService = bookAuthorService;
    }

    @GetMapping
    @SecurityRequirement(
            name = "Bearer authentication"
    )
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN', 'MODERATOR')")
    public ResponseEntity<Page<BookAuthorDto>> search(
            BookAuthorFilter filter,
            Pageable pageable
    ) {
        Page<BookAuthorDto> bookAuthorPageable = bookAuthorService.getPageable(filter, pageable);

        return ResponseEntity.ok(bookAuthorPageable);
    }

    @PostMapping
    @SecurityRequirement(
            name = "Bearer authentication"
    )
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN', 'MODERATOR')")
    public ResponseEntity<BookAuthorDto> create(
            @RequestBody CreateBookAuthorCommand command
    ) {
        BookAuthorDto bookAuthor = bookAuthorService.create(command);

        return ResponseEntity.ok(bookAuthor);
    }

    @PutMapping("/{id}")
    @SecurityRequirement(
            name = "Bearer authentication"
    )
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN', 'MODERATOR')")
    public ResponseEntity<BookAuthorDto> update(
            @PathVariable(value = "id") UUID id,
            @RequestBody CreateBookAuthorCommand command
    ) {
        BookAuthorDto updatedBookAuthor = bookAuthorService.update(id, command);

        return ResponseEntity.ok(updatedBookAuthor);
    }

    @GetMapping("/{id}")
    @SecurityRequirement(
            name = "Bearer authentication"
    )
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN', 'MODERATOR')")
    public ResponseEntity<BookAuthorDto> getById(
            @PathVariable(value = "id") UUID id
    ) {
        BookAuthorDto bookAuthor = bookAuthorService.getById(id);

        return ResponseEntity.ok(bookAuthor);
    }
}
