package com.vuongle.imaginepg.interfaces.rest.v1;

import com.vuongle.imaginepg.application.commands.CreateTaskCommand;
import com.vuongle.imaginepg.application.dto.TaskDto;
import com.vuongle.imaginepg.application.exceptions.UserNotFoundException;
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
@RequestMapping("/api/task")
public class TodoController {

    private final TodoService todoService;

    public TodoController(
            TodoService todoService
    ) {
        this.todoService = todoService;
    }

    @GetMapping
    @SecurityRequirement(name = "Bearer authentication")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN', 'MODERATOR')")
    public ResponseEntity<Page<TaskDto>> searchTask(
            TaskFilter taskFilter,
            Pageable pageable
    ) {
        Page<TaskDto> taskPage = todoService.getPageable(taskFilter, pageable);

        return ResponseEntity.ok(taskPage);
    }

    @GetMapping("/current-user")
    @SecurityRequirement(name = "Bearer authentication")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN', 'MODERATOR')")
    public ResponseEntity<Page<TaskDto>> findByCurrentUser(
            Pageable pageable
    ) {
        TaskFilter taskFilter = new TaskFilter();

        if (Context.getUser() == null) throw new UserNotFoundException("Not found current user");

        taskFilter.setUserId(Context.getUser().getId());
        Page<TaskDto> taskPage = todoService.getPageable(taskFilter, pageable);

        return ResponseEntity.ok(taskPage);
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "Bearer authentication")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN', 'MODERATOR')")
    public ResponseEntity<TaskDto> getById(
            @PathVariable(value = "id") UUID id
    ) {
        TaskDto task = todoService.getById(id);

        return ResponseEntity.ok(task);
    }

    @PostMapping
    @SecurityRequirement(name = "Bearer authentication")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN', 'MODERATOR')")
    public ResponseEntity<TaskDto> create(
            @RequestBody CreateTaskCommand command
    ) {
        TaskDto task = todoService.create(command);

        return ResponseEntity.ok(task);
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "Bearer authentication")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN', 'MODERATOR')")
    public ResponseEntity<TaskDto> update(
            @PathVariable(value = "id") UUID id,
            @RequestBody CreateTaskCommand command
    ) {
        TaskDto task = todoService.update(id, command);

        return ResponseEntity.ok(task);
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "Bearer authentication")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN', 'MODERATOR')")
    public ResponseEntity<Void> delete(
            @PathVariable(value = "id") UUID id
    ) {
        todoService.delete(id, false);

        return ResponseEntity.ok(null);
    }

}
