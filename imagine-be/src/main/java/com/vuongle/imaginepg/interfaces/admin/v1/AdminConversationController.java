package com.vuongle.imaginepg.interfaces.admin.v1;

import com.vuongle.imaginepg.application.commands.CreateConversationCommand;
import com.vuongle.imaginepg.application.dto.ConversationDto;
import com.vuongle.imaginepg.application.queries.ConversationFilter;
import com.vuongle.imaginepg.domain.services.ConversationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/admin/conversations")
public class AdminConversationController {

    private final ConversationService conversationService;

    public AdminConversationController(
            ConversationService conversationService
    ) {
        this.conversationService = conversationService;
    }

    @GetMapping
    @SecurityRequirement(name = "Bearer authentication")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODERATOR')")
    public ResponseEntity<Page<ConversationDto>> searchConversation(
            ConversationFilter filter,
            Pageable pageable
    ) {
        Page<ConversationDto> quizPage = conversationService.getPageable(filter, pageable);

        return ResponseEntity.ok(quizPage);
    }

    @PostMapping
    @SecurityRequirement(name = "Bearer authentication")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODERATOR')")
    public ResponseEntity<ConversationDto> create(
            @RequestBody CreateConversationCommand command
    ) {
        ConversationDto quiz = conversationService.create(command);

        return ResponseEntity.ok(quiz);
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "Bearer authentication")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODERATOR')")
    public ResponseEntity<ConversationDto> updateConversation(
            @PathVariable(value = "id") UUID id,
            @RequestBody CreateConversationCommand command
    ) {
        ConversationDto quiz = conversationService.update(id, command);

        return ResponseEntity.ok(quiz);
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "Bearer authentication")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODERATOR')")
    public ResponseEntity<ConversationDto> getById(
            @PathVariable(value = "id") UUID id
    ) {
        ConversationDto quiz = conversationService.getById(id);

        return ResponseEntity.ok(quiz);
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "Bearer authentication")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODERATOR')")
    public ResponseEntity<Void> deleteById(
            @PathVariable(value = "id") UUID id
    ) {
        conversationService.delete(id, false);

        return ResponseEntity.ok(null);
    }
}
