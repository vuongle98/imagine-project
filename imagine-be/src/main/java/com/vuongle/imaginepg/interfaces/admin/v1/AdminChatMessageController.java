package com.vuongle.imaginepg.interfaces.admin.v1;

import com.vuongle.imaginepg.application.commands.CreateChatMessageCommand;
import com.vuongle.imaginepg.application.dto.ChatMessageDto;
import com.vuongle.imaginepg.application.queries.ChatMessageFilter;
import com.vuongle.imaginepg.domain.services.ChatMessageService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/admin/chat-message")
public class AdminChatMessageController {

    private final ChatMessageService chatMessageService;

    public AdminChatMessageController(
            ChatMessageService chatMessageService
    ) {
        this.chatMessageService = chatMessageService;
    }

    @GetMapping
    @SecurityRequirement(
            name = "Bearer authentication"
    )
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODERATOR')")
    public ResponseEntity<Page<ChatMessageDto>> searchQuestion(
            ChatMessageFilter chatMessageFilter,
            Pageable pageable
    ) {
        Page<ChatMessageDto> quizPage = chatMessageService.getPageable(chatMessageFilter, pageable);

        return ResponseEntity.ok(quizPage);
    }

    @PostMapping
    @SecurityRequirement(
            name = "Bearer authentication"
    )
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODERATOR')")
    public ResponseEntity<ChatMessageDto> createQuestion(
            CreateChatMessageCommand command
    ) {
        ChatMessageDto quiz = chatMessageService.create(command);

        return ResponseEntity.ok(quiz);
    }

    @PutMapping("/{id}")
    @SecurityRequirement(
            name = "Bearer authentication"
    )
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODERATOR')")
    public ResponseEntity<ChatMessageDto> updateQuestion(
            @PathVariable(value = "id") UUID id,
            CreateChatMessageCommand command
    ) {
        ChatMessageDto quiz = chatMessageService.update(id, command);

        return ResponseEntity.ok(quiz);
    }

    @GetMapping("/{id}")
    @SecurityRequirement(
            name = "Bearer authentication"
    )
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODERATOR')")
    public ResponseEntity<ChatMessageDto> getById(
            @PathVariable(value = "id") UUID id
    ) {
        ChatMessageDto quiz = chatMessageService.getById(id);

        return ResponseEntity.ok(quiz);
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(
            name = "Bearer authentication"
    )
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODERATOR')")
    public ResponseEntity<Void> delete(@PathVariable(value = "id") UUID id, @RequestParam(value = "force") boolean force) {
        chatMessageService.delete(id, force);

        return ResponseEntity.ok(null);
    }

}
