package com.vuongle.imaginepg.interfaces.admin.v1;

import com.vuongle.imaginepg.application.commands.CreateQuizCommand;
import com.vuongle.imaginepg.application.dto.QuizDto;
import com.vuongle.imaginepg.application.queries.QuizFilter;
import com.vuongle.imaginepg.domain.services.QuizService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/admin/quiz")
public class AdminQuizController {

    private final QuizService quizService;

    public AdminQuizController(
            QuizService quizService
    ) {
        this.quizService = quizService;
    }

    @GetMapping
    @SecurityRequirement(
            name = "Bearer authentication"
    )
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODERATOR')")
    public ResponseEntity<Page<QuizDto>> searchQuiz(
            QuizFilter quizFilter,
            Pageable pageable
    ) {
        Page<QuizDto> quizPage = quizService.getPageable(quizFilter, pageable);

        return ResponseEntity.ok(quizPage);
    }

    @PostMapping
    @SecurityRequirement(
            name = "Bearer authentication"
    )
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODERATOR')")
    public ResponseEntity<QuizDto> createQuiz(
            @RequestBody CreateQuizCommand command
    ) {
        QuizDto quiz = quizService.create(command);

        return ResponseEntity.ok(quiz);
    }

    @PutMapping("/{id}")
    @SecurityRequirement(
            name = "Bearer authentication"
    )
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODERATOR')")
    public ResponseEntity<QuizDto> updateQuiz(
            @PathVariable(value = "id") UUID id,
            @RequestBody CreateQuizCommand command
    ) {
        QuizDto quiz = quizService.update(id, command);

        return ResponseEntity.ok(quiz);
    }

    @GetMapping("/{id}")
    @SecurityRequirement(
            name = "Bearer authentication"
    )
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODERATOR')")
    public ResponseEntity<QuizDto> getById(
            @PathVariable(value = "id") UUID id
    ) {
        QuizDto quiz = quizService.getById(id);

        return ResponseEntity.ok(quiz);
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(
            name = "Bearer authentication"
    )
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODERATOR')")
    public ResponseEntity<Void> delete(@PathVariable(value = "id") UUID id, @RequestParam(value = "force") boolean force) {
        quizService.delete(id, force);

        return ResponseEntity.ok(null);
    }
}
