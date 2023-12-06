package com.vuongle.imaginepg.interfaces.rest.v1;

import com.vuongle.imaginepg.application.commands.CreatePostCommand;
import com.vuongle.imaginepg.application.dto.PostDto;
import com.vuongle.imaginepg.application.queries.PostFilter;
import com.vuongle.imaginepg.domain.services.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;

    public PostController(
            PostService postService
    ) {
        this.postService = postService;
    }

    @GetMapping()
    public ResponseEntity<Page<PostDto>> searchPost(
        PostFilter postFilter,
        Pageable pageable
    ) {
        Page<PostDto> postPageable = postService.getAll(postFilter, pageable);
        return ResponseEntity.ok(postPageable);
    }

    @GetMapping("/featured")
    public ResponseEntity<List<PostDto>> featured(
            PostFilter postFilter
    ) {
        postFilter.setFeatured(true);
        List<PostDto> postPageable = postService.getAll(postFilter);
        return ResponseEntity.ok(postPageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getById(
            @PathVariable(value = "id") UUID id
    ) {
        PostDto post = postService.getById(id);

        return ResponseEntity.ok(post);
    }

    @PostMapping
    public ResponseEntity<PostDto> create(
            @RequestBody CreatePostCommand command
    ) {
        PostDto post = postService.create(command);

        return ResponseEntity.ok(post);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDto> update(
            @PathVariable(value = "id") UUID id,
            @RequestBody CreatePostCommand command
    ) {
        PostDto post = postService.update(id, command);

        return ResponseEntity.ok(post);
    }
}
