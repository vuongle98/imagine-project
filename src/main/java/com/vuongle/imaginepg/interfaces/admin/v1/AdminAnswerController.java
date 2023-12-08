package com.vuongle.imaginepg.interfaces.admin.v1;

import com.vuongle.imaginepg.application.commands.CreateAnswerCommand;
import com.vuongle.imaginepg.application.dto.AnswerDto;
import com.vuongle.imaginepg.application.queries.AnswerFilter;
import com.vuongle.imaginepg.domain.services.AnswerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Page<AnswerDto>> searchQuestion(
            AnswerFilter answerFilter,
            Pageable pageable
    ) {
        Page<AnswerDto> quizPage = answerService.getAll(answerFilter, pageable);

        return ResponseEntity.ok(quizPage);
    }

    @PostMapping
    public ResponseEntity<AnswerDto> createQuestion(
            CreateAnswerCommand command
    ) {
        AnswerDto quiz = answerService.create(command);

        return ResponseEntity.ok(quiz);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AnswerDto> updateQuestion(
            @PathVariable(value = "id") UUID id,
            CreateAnswerCommand command
    ) {
        AnswerDto quiz = answerService.update(id, command);

        return ResponseEntity.ok(quiz);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnswerDto> getById(
            @PathVariable(value = "id") UUID id
    ) {
        AnswerDto quiz = answerService.getById(id);

        return ResponseEntity.ok(quiz);
    }

}
