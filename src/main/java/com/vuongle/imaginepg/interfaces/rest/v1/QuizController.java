package com.vuongle.imaginepg.interfaces.rest.v1;

import com.vuongle.imaginepg.application.commands.CreateQuizCommand;
import com.vuongle.imaginepg.application.dto.QuizDto;
import com.vuongle.imaginepg.application.queries.QuizFilter;
import com.vuongle.imaginepg.domain.services.QuizService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/quiz")
public class QuizController {

    private final QuizService quizService;

    public QuizController(
            QuizService quizService
    ) {
        this.quizService = quizService;
    }

    @GetMapping
    public ResponseEntity<Page<QuizDto>> searchQuiz(
            QuizFilter quizFilter,
            Pageable pageable
    ) {
        Page<QuizDto> quizPage = quizService.getAll(quizFilter, pageable);

        return ResponseEntity.ok(quizPage);
    }

    @PostMapping
    public ResponseEntity<QuizDto> createQuiz(
            CreateQuizCommand command
    ) {
        QuizDto quiz = quizService.create(command);

        return ResponseEntity.ok(quiz);
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuizDto> updateQuiz(
            @PathVariable(value = "id") UUID id,
            CreateQuizCommand command
    ) {
        QuizDto quiz = quizService.update(id, command);

        return ResponseEntity.ok(quiz);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuizDto> getById(
            @PathVariable(value = "id") UUID id
    ) {
        QuizDto quiz = quizService.getById(id);

        return ResponseEntity.ok(quiz);
    }
}
