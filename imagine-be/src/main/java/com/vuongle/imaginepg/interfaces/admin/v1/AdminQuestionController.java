package com.vuongle.imaginepg.interfaces.admin.v1;

import com.vuongle.imaginepg.application.commands.CreateQuestionCommand;
import com.vuongle.imaginepg.application.dto.QuestionDto;
import com.vuongle.imaginepg.application.queries.QuestionFilter;
import com.vuongle.imaginepg.domain.services.QuestionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/admin/question")
public class AdminQuestionController {

    private final QuestionService questionService;

    public AdminQuestionController(
            QuestionService questionService
    ) {
        this.questionService = questionService;
    }

    @GetMapping
    @SecurityRequirement(
            name = "Bearer authentication"
    )
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODERATOR')")
    public ResponseEntity<Page<QuestionDto>> searchQuestion(
            QuestionFilter questionFilter,
            Pageable pageable
    ) {
        Page<QuestionDto> quizPage = questionService.getPageable(questionFilter, pageable);

        return ResponseEntity.ok(quizPage);
    }

    @PostMapping
    @SecurityRequirement(
            name = "Bearer authentication"
    )
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODERATOR')")
    public ResponseEntity<QuestionDto> createQuestion(
            @RequestBody CreateQuestionCommand command
    ) {
        QuestionDto quiz = questionService.create(command);

        return ResponseEntity.ok(quiz);
    }

    @PutMapping("/{id}")
    @SecurityRequirement(
            name = "Bearer authentication"
    )
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODERATOR')")
    public ResponseEntity<QuestionDto> updateQuestion(
            @PathVariable(value = "id") UUID id,
            @RequestBody CreateQuestionCommand command
    ) {
        QuestionDto quiz = questionService.update(id, command);

        return ResponseEntity.ok(quiz);
    }

    @GetMapping("/{id}")
    @SecurityRequirement(
            name = "Bearer authentication"
    )
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODERATOR')")
    public ResponseEntity<QuestionDto> getById(
            @PathVariable(value = "id") UUID id
    ) {
        QuestionDto quiz = questionService.getById(id);

        return ResponseEntity.ok(quiz);
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(
            name = "Bearer authentication"
    )
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODERATOR')")
    public ResponseEntity<Void> delete(@PathVariable(value = "id") UUID id, @RequestParam(value = "force", required = false) boolean force) {
        questionService.delete(id, force);

        return ResponseEntity.ok(null);
    }

}
