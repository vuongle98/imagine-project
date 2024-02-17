package com.vuongle.imaginepg.interfaces.rest.v1;

import com.vuongle.imaginepg.application.commands.CreateCommentCommand;
import com.vuongle.imaginepg.application.dto.CommentDto;
import com.vuongle.imaginepg.application.queries.CommentFilter;
import com.vuongle.imaginepg.domain.services.CommentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentService commentService;

    public CommentController(
            CommentService commentService
    ) {
        this.commentService = commentService;
    }

    @GetMapping
    @SecurityRequirement(name = "Bearer authentication")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN', 'MODERATOR')")
    public ResponseEntity<Page<CommentDto>> searchComment(
            CommentFilter filter,
            Pageable pageable
    ) {
        Page<CommentDto> commentPage = commentService.getAll(filter, pageable);

        return ResponseEntity.ok(commentPage);
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "Bearer authentication")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN', 'MODERATOR')")
    public ResponseEntity<CommentDto> getById(
            @PathVariable(value = "id") UUID id
    ) {
        CommentDto comment = commentService.getById(id);

        return ResponseEntity.ok(comment);
    }

    @PostMapping
    @SecurityRequirement(name = "Bearer authentication")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN', 'MODERATOR')")
    public ResponseEntity<CommentDto> create(
            @RequestBody CreateCommentCommand command
    ) {
        CommentDto comment = commentService.create(command);

        return ResponseEntity.ok(comment);
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "Bearer authentication")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN', 'MODERATOR')")
    public ResponseEntity<CommentDto> update(
            @PathVariable(value = "id") UUID id,
            @RequestBody CreateCommentCommand command
    ) {
        CommentDto comment = commentService.update(id, command);

        return ResponseEntity.ok(comment);
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "Bearer authentication")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN', 'MODERATOR')")
    public ResponseEntity<Void> deleteById(
            @PathVariable(value = "id") UUID id
    ) {
        commentService.delete(id, false);

        return ResponseEntity.ok(null);
    }
}
