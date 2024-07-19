package com.vuongle.imaginepg.interfaces.rest.v1;

import com.vuongle.imaginepg.application.dto.TagDto;
import com.vuongle.imaginepg.application.queries.TagFilter;
import com.vuongle.imaginepg.domain.services.TagService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tag")
public class TagController {

    private final TagService tagService;

    public TagController(
            TagService tagService
    ) {
        this.tagService = tagService;
    }

    @GetMapping
    @SecurityRequirement(name = "Bearer authentication")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN', 'MODERATOR')")
    public ResponseEntity<Page<TagDto>> searchTag(
            TagFilter tagFilter,
            Pageable pageable
    ) {
        Page<TagDto> quizPage = tagService.getPageable(tagFilter, pageable);

        return ResponseEntity.ok(quizPage);
    }

}
