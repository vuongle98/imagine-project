package com.vuongle.imaginepg.interfaces.rest.v1;

import com.vuongle.imaginepg.application.commands.CreateTaskCommand;
import com.vuongle.imaginepg.application.dto.TaskDto;
import com.vuongle.imaginepg.application.queries.TaskFilter;
import com.vuongle.imaginepg.domain.entities.Task;
import com.vuongle.imaginepg.domain.services.TodoService;
import com.vuongle.imaginepg.shared.utils.Context;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/task")
public class TodoController {

    private TodoService todoService;

    public TodoController(
            TodoService todoService
    ) {
        this.todoService = todoService;
    }

    @GetMapping
    public ResponseEntity<Page<TaskDto>> searchTask(
        TaskFilter taskFilter,
        Pageable pageable
    ) {
        Page<TaskDto> taskPage = todoService.getAll(taskFilter, pageable);

        return ResponseEntity.ok(taskPage);
    }

    @GetMapping("/current-user")
    public ResponseEntity<Page<TaskDto>> findByCurrentUser(
            Pageable pageable
    ) {
        TaskFilter taskFilter = new TaskFilter();
        taskFilter.setUserId(Context.getUser().getId());
        Page<TaskDto> taskPage = todoService.getAll(taskFilter, pageable);

        return ResponseEntity.ok(taskPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getById(
            @PathVariable(value = "id") UUID id
    ) {
        TaskDto task = todoService.getById(id);

        return ResponseEntity.ok(task);
    }

    @PostMapping
    public ResponseEntity<TaskDto> create(
            CreateTaskCommand command
    ) {
        TaskDto task = todoService.create(command);

        return ResponseEntity.ok(task);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDto> update(
            @PathVariable(value = "id") UUID id,
            CreateTaskCommand command
    ) {
        TaskDto task = todoService.update(id, command);

        return ResponseEntity.ok(task);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable(value = "id") UUID id,
            @RequestParam(value = "force") boolean force
    ) {
        todoService.delete(id, force);

        return ResponseEntity.ok(null);
    }

}
