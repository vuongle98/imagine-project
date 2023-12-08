package com.vuongle.imaginepg.interfaces.rest.v1;

import com.vuongle.imaginepg.application.commands.CreateTagCommand;
import com.vuongle.imaginepg.application.dto.TagDto;
import com.vuongle.imaginepg.application.queries.TagFilter;
import com.vuongle.imaginepg.domain.services.TagService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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
    public ResponseEntity<Page<TagDto>> searchQuiz(
            TagFilter tagFilter,
            Pageable pageable
    ) {
        Page<TagDto> quizPage = tagService.getAll(tagFilter, pageable);

        return ResponseEntity.ok(quizPage);
    }

    @PostMapping
    public ResponseEntity<TagDto> createQuiz(
            CreateTagCommand command
    ) {
        TagDto quiz = tagService.create(command);

        return ResponseEntity.ok(quiz);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TagDto> updateQuiz(
            @PathVariable(value = "id") UUID id,
            CreateTagCommand command
    ) {
        TagDto quiz = tagService.update(id, command);

        return ResponseEntity.ok(quiz);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagDto> getById(
            @PathVariable(value = "id") UUID id
    ) {
        TagDto quiz = tagService.getById(id);

        return ResponseEntity.ok(quiz);
    }
    
}
