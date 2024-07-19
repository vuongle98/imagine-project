package com.vuongle.imaginepg.interfaces.admin.v1;

import com.vuongle.imaginepg.application.commands.CreateAnswerCommand;
import com.vuongle.imaginepg.application.dto.AnswerDto;
import com.vuongle.imaginepg.application.queries.AnswerFilter;
import com.vuongle.imaginepg.domain.services.AnswerService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/admin/answer")
public class AdminAnswerController {

    private final AnswerService answerService;

    public AdminAnswerController(
            AnswerService answerService
    ) {
        this.answerService = answerService;
    }

    @GetMapping
    @SecurityRequirement(
            name = "Bearer authentication"
    )
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODERATOR')")
    public ResponseEntity<Page<AnswerDto>> searchQuestion(
            AnswerFilter answerFilter,
            Pageable pageable
    ) {
        Page<AnswerDto> quizPage = answerService.getPageable(answerFilter, pageable);

        return ResponseEntity.ok(quizPage);
    }

    @PostMapping
    @SecurityRequirement(
            name = "Bearer authentication"
    )
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODERATOR')")
    public ResponseEntity<AnswerDto> createQuestion(
            @RequestBody CreateAnswerCommand command
    ) {
        AnswerDto quiz = answerService.create(command);

        return ResponseEntity.ok(quiz);
    }

    @PutMapping("/{id}")
    @SecurityRequirement(
            name = "Bearer authentication"
    )
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODERATOR')")
    public ResponseEntity<AnswerDto> updateQuestion(
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
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODERATOR')")
    public ResponseEntity<AnswerDto> getById(
            @PathVariable(value = "id") UUID id
    ) {
        AnswerDto quiz = answerService.getById(id);

        return ResponseEntity.ok(quiz);
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(
            name = "Bearer authentication"
    )
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODERATOR')")
    public ResponseEntity<Void> delete(@PathVariable(value = "id") UUID id, @RequestParam(value = "force") boolean force) {
        answerService.delete(id, force);

        return ResponseEntity.ok(null);
    }

}
