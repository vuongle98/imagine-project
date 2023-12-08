package com.vuongle.imaginepg.interfaces.admin.v1;

import com.vuongle.imaginepg.application.commands.CreateTagCommand;
import com.vuongle.imaginepg.application.dto.TagDto;
import com.vuongle.imaginepg.application.queries.TagFilter;
import com.vuongle.imaginepg.domain.services.TagService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/admin/tag")
public class AdminTagController {

    private final TagService tagService;

    public AdminTagController(
            TagService tagService
    ) {
        this.tagService = tagService;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODERATOR')")
    @GetMapping
    public ResponseEntity<Page<TagDto>> searchTag(
            TagFilter tagFilter,
            Pageable pageable
    ) {
        Page<TagDto> tagPage = tagService.getAll(tagFilter, pageable);

        return ResponseEntity.ok(tagPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagDto> getById(
            @PathVariable(value = "id") UUID id
    ) {
        TagDto tag = tagService.getById(id);

        return ResponseEntity.ok(tag);
    }

    @PostMapping
    public ResponseEntity<TagDto> createTag(
            CreateTagCommand command
    ) {
        TagDto tag = tagService.create(command);

        return ResponseEntity.ok(tag);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TagDto> updateTag(
            @PathVariable(value = "id") UUID id,
            CreateTagCommand command
    ) {
        TagDto tag = tagService.update(id, command);

        return ResponseEntity.ok(tag);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable(value = "id") UUID id, @RequestParam(value = "force") boolean force) {
        tagService.delete(id, force);

        return ResponseEntity.ok(null);
    }

}
