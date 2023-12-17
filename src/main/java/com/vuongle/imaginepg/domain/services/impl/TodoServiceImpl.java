package com.vuongle.imaginepg.domain.services.impl;

import com.vuongle.imaginepg.application.commands.CreateTaskCommand;
import com.vuongle.imaginepg.application.dto.TaskDto;
import com.vuongle.imaginepg.application.exceptions.NoPermissionException;
import com.vuongle.imaginepg.application.queries.TaskFilter;
import com.vuongle.imaginepg.domain.entities.Task;
import com.vuongle.imaginepg.domain.repositories.TaskRepository;
import com.vuongle.imaginepg.domain.services.TodoService;
import com.vuongle.imaginepg.infrastructure.specification.TaskSpecifications;
import com.vuongle.imaginepg.shared.utils.Context;
import com.vuongle.imaginepg.shared.utils.ObjectData;
import com.vuongle.imaginepg.shared.utils.ValidateResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@Transactional
public class TodoServiceImpl implements TodoService {

    private final TaskRepository taskRepository;

    public TodoServiceImpl(
            TaskRepository taskRepository
    ) {
        this.taskRepository = taskRepository;
    }

    @Override
    public TaskDto getById(UUID id) {
        return getById(id, TaskDto.class);
    }

    @Override
    public <R> R getById(UUID id, Class<R> classType) {
        Task task = taskRepository.getById(id);
        // check permission
        if (!Context.hasModifyPermission() && !ValidateResource.isOwnResource(task, Task.class)) {
            throw new NoPermissionException("No permission");
        }

        return ObjectData.mapTo(task, classType);
    }

    @Override
    public TaskDto create(CreateTaskCommand command) {

        Task task = ObjectData.mapTo(command, Task.class);

        task.setUser(Context.getUser());

        task = taskRepository.save(task);

        return ObjectData.mapTo(task, TaskDto.class);
    }

    @Override
    public TaskDto update(UUID id, CreateTaskCommand command) {

        Task existed = getById(id, Task.class);

        if (Objects.nonNull(command.getDescription())) {
            existed.setDescription(command.getDescription());
        }

        if (Objects.nonNull(command.getColor())) {
            existed.setColor(command.getColor());
        }

        existed.setPinned(command.isPinned());

        if (command.isCompleted()) {
            existed.setCompletedAt(Instant.now());
        } else {
            existed.setCompletedAt(null);
        }

        existed = taskRepository.save(existed);
        return ObjectData.mapTo(existed, TaskDto.class);
    }

    @Override
    public void delete(UUID id, boolean force) {

        Task task = getById(id, Task.class);

        if (force) {
            taskRepository.deleteById(id);
        } else {
            task.setDeletedAt(Instant.now());
            taskRepository.save(task);
        }

    }

    @Override
    public Page<TaskDto> getAll(TaskFilter filter, Pageable pageable) {
        Specification<Task> specification = TaskSpecifications.withFilter(filter);
        Page<Task> taskPage = taskRepository.findAll(specification, pageable);
        return taskPage.map(task -> ObjectData.mapTo(task, TaskDto.class));
    }

    @Override
    public List<TaskDto> getAll(TaskFilter filter) {
        Specification<Task> specification = TaskSpecifications.withFilter(filter);
        return ObjectData.mapListTo(taskRepository.findAll(specification), TaskDto.class);
    }
}
