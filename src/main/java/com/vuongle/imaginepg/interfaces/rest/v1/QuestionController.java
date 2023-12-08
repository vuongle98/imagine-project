package com.vuongle.imaginepg.interfaces.rest.v1;

import com.vuongle.imaginepg.application.commands.CreateQuestionCommand;
import com.vuongle.imaginepg.application.dto.QuestionDto;
import com.vuongle.imaginepg.application.queries.QuestionFilter;
import com.vuongle.imaginepg.domain.services.QuestionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/question")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(
            QuestionService questionService
    ) {
        this.questionService = questionService;
    }

    @GetMapping
    public ResponseEntity<Page<QuestionDto>> searchQuestion(
            QuestionFilter questionFilter,
            Pageable pageable
    ) {
        Page<QuestionDto> quizPage = questionService.getAll(questionFilter, pageable);

        return ResponseEntity.ok(quizPage);
    }

    @PostMapping
    public ResponseEntity<QuestionDto> createQuestion(
            CreateQuestionCommand command
    ) {
        QuestionDto quiz = questionService.create(command);

        return ResponseEntity.ok(quiz);
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuestionDto> updateQuestion(
            @PathVariable(value = "id") UUID id,
            CreateQuestionCommand command
    ) {
        QuestionDto quiz = questionService.update(id, command);

        return ResponseEntity.ok(quiz);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestionDto> getById(
            @PathVariable(value = "id") UUID id
    ) {
        QuestionDto quiz = questionService.getById(id);

        return ResponseEntity.ok(quiz);
    }

}
