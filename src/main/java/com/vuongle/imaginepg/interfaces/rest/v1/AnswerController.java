package com.vuongle.imaginepg.interfaces.rest.v1;

import com.vuongle.imaginepg.application.commands.CreateAnswerCommand;
import com.vuongle.imaginepg.application.dto.AnswerDto;
import com.vuongle.imaginepg.application.queries.AnswerFilter;
import com.vuongle.imaginepg.domain.services.AnswerService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/answer")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(
        name = "Auth",
        description = "CRUD REST APIs for Answer"
)
public class AnswerController {

    private final AnswerService answerService;

    public AnswerController(
            AnswerService answerService
    ) {
        this.answerService = answerService;
    }

    @GetMapping
    @SecurityRequirement(
            name = "Bearer authentication"
    )
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN', 'MODERATOR')")
    public ResponseEntity<Page<AnswerDto>> searchAnswer(
            AnswerFilter answerFilter,
            Pageable pageable
    ) {
        Page<AnswerDto> quizPage = answerService.getAll(answerFilter, pageable);

        return ResponseEntity.ok(quizPage);
    }

    @PostMapping
    @SecurityRequirement(
            name = "Bearer authentication"
    )
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN', 'MODERATOR')")
    public ResponseEntity<AnswerDto> createAnswer(
            @RequestBody CreateAnswerCommand command
    ) {
        AnswerDto quiz = answerService.create(command);

        return ResponseEntity.ok(quiz);
    }

    @PutMapping("/{id}")
    @SecurityRequirement(
            name = "Bearer authentication"
    )
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN', 'MODERATOR')")
    public ResponseEntity<AnswerDto> updateAnswer(
            @PathVariable(value = "id") UUID id,
            @RequestBody CreateAnswerCommand command
    ) {
        AnswerDto quiz = answerService.update(id, command);

        return ResponseEntity.ok(quiz);
    }

    @GetMapping("/{id}")
    @SecurityRequirement(
            name = "Bearer authentication"
    )
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN', 'MODERATOR')")
    public ResponseEntity<AnswerDto> getById(
            @PathVariable(value = "id") UUID id
    ) {
        AnswerDto quiz = answerService.getById(id);

        return ResponseEntity.ok(quiz);
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "Bearer authentication")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN', 'MODERATOR')")
    public ResponseEntity<Void> deleteById(
            @PathVariable(value = "id") UUID id
    ) {
        answerService.delete(id, false);

        return ResponseEntity.ok(null);
    }
}
