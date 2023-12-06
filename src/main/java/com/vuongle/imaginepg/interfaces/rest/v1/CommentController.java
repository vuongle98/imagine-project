package com.vuongle.imaginepg.interfaces.rest.v1;

import com.vuongle.imaginepg.application.commands.CreateCommentCommand;
import com.vuongle.imaginepg.application.dto.CommentDto;
import com.vuongle.imaginepg.application.queries.CommentFilter;
import com.vuongle.imaginepg.domain.services.CommentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Page<CommentDto>> searchComment(
            CommentFilter filter,
            Pageable pageable
    ) {
        Page<CommentDto> commentPage = commentService.getAll(filter, pageable);

        return ResponseEntity.ok(commentPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDto> getById(
            @PathVariable(value = "id") UUID id
    ) {
        CommentDto comment = commentService.getById(id);

        return ResponseEntity.ok(comment);
    }

    @PostMapping
    public ResponseEntity<CommentDto> create(
            @RequestBody CreateCommentCommand command
    ) {
        CommentDto comment = commentService.create(command);

        return ResponseEntity.ok(comment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentDto> update(
            @PathVariable(value = "id") UUID id,
            @RequestBody CreateCommentCommand command
    ) {
        CommentDto comment = commentService.update(id, command);

        return ResponseEntity.ok(comment);
    }
}
