package com.vuongle.imaginepg.domain.services.impl;

import com.vuongle.imaginepg.application.commands.CreateAnswerCommand;
import com.vuongle.imaginepg.application.dto.AnswerDto;
import com.vuongle.imaginepg.application.exceptions.NoPermissionException;
import com.vuongle.imaginepg.application.queries.AnswerFilter;
import com.vuongle.imaginepg.domain.entities.Answer;
import com.vuongle.imaginepg.domain.repositories.BaseRepository;
import com.vuongle.imaginepg.domain.services.AnswerService;
import com.vuongle.imaginepg.infrastructure.specification.AnswerSpecifications;
import com.vuongle.imaginepg.shared.utils.Context;
import com.vuongle.imaginepg.shared.utils.ObjectData;
import com.vuongle.imaginepg.shared.utils.ValidateResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@Transactional
public class AnswerServiceImpl implements AnswerService {

    private final BaseRepository<Answer> answerRepository;

    public AnswerServiceImpl(
            BaseRepository<Answer> answerRepository
    ) {
        this.answerRepository = answerRepository;
    }

    @Override
    public AnswerDto getById(UUID id) {
        return getById(id, AnswerDto.class);
    }

    @Override
    public <R> R getById(UUID id, Class<R> classType) {
        Answer answer = answerRepository.getById(id);

        // check permission
        if (!Context.hasModifyPermission() && !ValidateResource.isOwnResource(answer, Answer.class)) {
            throw new NoPermissionException("No permission");
        }

        return ObjectData.mapTo(answer, classType);
    }

    @Override
    public AnswerDto create(CreateAnswerCommand command) {

        Answer answer = ObjectData.mapTo(command, Answer.class);

        answer.setUser(Context.getUser());

        answer = answerRepository.save(answer);
        return ObjectData.mapTo(answer, AnswerDto.class);
    }

    @Override
    public AnswerDto update(UUID id, CreateAnswerCommand command) {
        Answer answer = getById(id, Answer.class);

        if (Objects.nonNull(command.getContent())) {
            answer.setContent(command.getContent());
        }

        if (Objects.nonNull(command.getCorrect())) {
            answer.setCorrect(command.getCorrect());
        }

        answer = answerRepository.save(answer);
        return ObjectData.mapTo(answer, AnswerDto.class);
    }

    @Override
    public void delete(UUID id, boolean force) {

        Answer answer = getById(id, Answer.class);

        if (force) {
            answerRepository.deleteById(id);
            return;
        }


    }

    @Override
    public Page<AnswerDto> getAll(AnswerFilter filter, Pageable pageable) {
        Specification<Answer> answerSpecification = AnswerSpecifications.withFilter(filter);

        Page<Answer> answerPage = answerRepository.findAll(answerSpecification, pageable);
        return answerPage.map(a -> ObjectData.mapTo(a, AnswerDto.class));
    }

    @Override
    public List<AnswerDto> getAll(AnswerFilter filter) {
        Specification<Answer> answerSpecification = AnswerSpecifications.withFilter(filter);

        return ObjectData.mapListTo(answerRepository.findAll(answerSpecification), AnswerDto.class);
    }
}
