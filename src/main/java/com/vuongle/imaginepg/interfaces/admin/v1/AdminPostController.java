package com.vuongle.imaginepg.interfaces.admin.v1;

import com.vuongle.imaginepg.application.commands.CreatePostCommand;
import com.vuongle.imaginepg.application.dto.PostDto;
import com.vuongle.imaginepg.application.queries.PostFilter;
import com.vuongle.imaginepg.domain.services.PostService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/post")
public class AdminPostController {

    private final PostService postService;

    public AdminPostController(
            PostService postService
    ) {
        this.postService = postService;
    }

    @GetMapping()
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODERATOR')")
    @SecurityRequirement(
            name = "Bearer authentication"
    )
    public ResponseEntity<Page<PostDto>> searchPost(
        PostFilter postFilter,
        Pageable pageable
    ) {
        Page<PostDto> postPageable = postService.getAll(postFilter, pageable);
        return ResponseEntity.ok(postPageable);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODERATOR')")
    @SecurityRequirement(
            name = "Bearer authentication"
    )
    public ResponseEntity<PostDto> getById(
            @PathVariable(value = "id") UUID id
    ) {
        PostDto post = postService.getById(id);

        return ResponseEntity.ok(post);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODERATOR')")
    @SecurityRequirement(
            name = "Bearer authentication"
    )
    public ResponseEntity<PostDto> create(
            @RequestBody CreatePostCommand command
    ) {
        PostDto post = postService.create(command);

        return ResponseEntity.ok(post);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODERATOR')")
    @SecurityRequirement(
            name = "Bearer authentication"
    )
    public ResponseEntity<PostDto> update(
            @PathVariable(value = "id") UUID id,
            @RequestBody CreatePostCommand command
    ) {
        PostDto post = postService.update(id, command);

        return ResponseEntity.ok(post);
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(
            name = "Bearer authentication"
    )
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODERATOR')")
    public ResponseEntity<Void> delete(@PathVariable(value = "id") UUID id, @RequestParam(value = "force") boolean force) {
        postService.delete(id, force);

        return ResponseEntity.ok(null);
    }
}
