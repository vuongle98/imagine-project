package com.vuongle.imaginepg.interfaces.rest.v1;

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
@RequestMapping("/api/chat-message")
public class ChatMessageController {

    private final ChatMessageService chatMessageService;

    public ChatMessageController(
            ChatMessageService chatMessageService
    ) {
        this.chatMessageService = chatMessageService;
    }

    @GetMapping
    @SecurityRequirement(name = "Bearer authentication")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN', 'MODERATOR')")
    public ResponseEntity<Page<ChatMessageDto>> searchMessage(
            ChatMessageFilter chatMessageFilter,
            Pageable pageable
    ) {
        Page<ChatMessageDto> quizPage = chatMessageService.getPageable(chatMessageFilter, pageable);

        return ResponseEntity.ok(quizPage);
    }

    @PostMapping
    @SecurityRequirement(name = "Bearer authentication")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN', 'MODERATOR')")
    public ResponseEntity<ChatMessageDto> createMessage(
            @RequestBody CreateChatMessageCommand command
    ) {
        ChatMessageDto quiz = chatMessageService.create(command);

        return ResponseEntity.ok(quiz);
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "Bearer authentication")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN', 'MODERATOR')")
    public ResponseEntity<ChatMessageDto> updateMessage(
            @PathVariable(value = "id") UUID id,
            @RequestBody CreateChatMessageCommand command
    ) {
        ChatMessageDto quiz = chatMessageService.update(id, command);

        return ResponseEntity.ok(quiz);
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "Bearer authentication")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN', 'MODERATOR')")
    public ResponseEntity<ChatMessageDto> getById(
            @PathVariable(value = "id") UUID id
    ) {
        ChatMessageDto quiz = chatMessageService.getById(id);

        return ResponseEntity.ok(quiz);
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "Bearer authentication")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN', 'MODERATOR')")
    public ResponseEntity<Void> deleteById(
            @PathVariable(value = "id") UUID id
    ) {
        chatMessageService.delete(id, false);

        return ResponseEntity.ok(null);
    }

}
