package com.vuongle.imaginepg.interfaces.admin.v1;

import com.vuongle.imaginepg.application.commands.CreateTaskCommand;
import com.vuongle.imaginepg.application.dto.TaskDto;
import com.vuongle.imaginepg.application.queries.TaskFilter;
import com.vuongle.imaginepg.domain.services.TodoService;
import com.vuongle.imaginepg.shared.utils.Context;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/admin/task")
public class AdminTodoController {

    private TodoService todoService;

    public AdminTodoController(
            TodoService todoService
    ) {
        this.todoService = todoService;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODERATOR')")
    @SecurityRequirement(
            name = "Bearer authentication"
    )
    public ResponseEntity<Page<TaskDto>> searchTask(
        TaskFilter taskFilter,
        Pageable pageable
    ) {
        Page<TaskDto> taskPage = todoService.getAll(taskFilter, pageable);

        return ResponseEntity.ok(taskPage);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODERATOR')")
    @SecurityRequirement(
            name = "Bearer authentication"
    )
    public ResponseEntity<TaskDto> getById(
            @PathVariable(value = "id") UUID id
    ) {
        TaskDto task = todoService.getById(id);

        return ResponseEntity.ok(task);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODERATOR')")
    @SecurityRequirement(
            name = "Bearer authentication"
    )
    public ResponseEntity<TaskDto> create(
            CreateTaskCommand command
    ) {
        TaskDto task = todoService.create(command);

        return ResponseEntity.ok(task);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODERATOR')")
    @SecurityRequirement(
            name = "Bearer authentication"
    )
    public ResponseEntity<TaskDto> update(
            @PathVariable(value = "id") UUID id,
            CreateTaskCommand command
    ) {
        TaskDto task = todoService.update(id, command);

        return ResponseEntity.ok(task);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODERATOR')")
    @SecurityRequirement(
            name = "Bearer authentication"
    )
    public ResponseEntity<Void> delete(
            @PathVariable(value = "id") UUID id,
            @RequestParam(value = "force") boolean force
    ) {
        todoService.delete(id, force);

        return ResponseEntity.ok(null);
    }

}
