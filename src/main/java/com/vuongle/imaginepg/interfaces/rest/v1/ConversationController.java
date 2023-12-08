package com.vuongle.imaginepg.interfaces.rest.v1;

import com.vuongle.imaginepg.application.commands.CreateConversationCommand;
import com.vuongle.imaginepg.application.dto.ConversationDto;
import com.vuongle.imaginepg.application.queries.ConversationFilter;
import com.vuongle.imaginepg.domain.services.ConversationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/conversations")
public class ConversationController {

    private final ConversationService conversationService;

    public ConversationController(
            ConversationService conversationService
    ) {
        this.conversationService = conversationService;
    }

    @GetMapping
    public ResponseEntity<Page<ConversationDto>> searchQuestion(
            ConversationFilter conversationFilter,
            Pageable pageable
    ) {
        Page<ConversationDto> quizPage = conversationService.getAll(conversationFilter, pageable);

        return ResponseEntity.ok(quizPage);
    }

    @PostMapping
    public ResponseEntity<ConversationDto> createQuestion(
            CreateConversationCommand command
    ) {
        ConversationDto quiz = conversationService.create(command);

        return ResponseEntity.ok(quiz);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConversationDto> updateQuestion(
            @PathVariable(value = "id") UUID id,
            CreateConversationCommand command
    ) {
        ConversationDto quiz = conversationService.update(id, command);

        return ResponseEntity.ok(quiz);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConversationDto> getById(
            @PathVariable(value = "id") UUID id
    ) {
        ConversationDto quiz = conversationService.getById(id);

        return ResponseEntity.ok(quiz);
    }


}
