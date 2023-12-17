package com.vuongle.imaginepg.domain.services.impl;

import com.vuongle.imaginepg.application.commands.CreateQuestionCommand;
import com.vuongle.imaginepg.application.dto.QuestionDto;
import com.vuongle.imaginepg.application.exceptions.NoPermissionException;
import com.vuongle.imaginepg.application.queries.AnswerFilter;
import com.vuongle.imaginepg.application.queries.QuestionFilter;
import com.vuongle.imaginepg.domain.entities.Answer;
import com.vuongle.imaginepg.domain.entities.Question;
import com.vuongle.imaginepg.domain.repositories.BaseRepository;
import com.vuongle.imaginepg.domain.services.QuestionService;
import com.vuongle.imaginepg.infrastructure.specification.AnswerSpecifications;
import com.vuongle.imaginepg.infrastructure.specification.QuestionSpecifications;
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
public class QuestionServiceImpl implements QuestionService {

    private final BaseRepository<Question> questionRepository;

    private final BaseRepository<Answer> answerRepository;

    public QuestionServiceImpl(
            BaseRepository<Question> questionRepository,
            BaseRepository<Answer> answerRepository
    ) {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }
    @Override
    public QuestionDto getById(UUID id) {
        return getById(id, QuestionDto.class);
    }

    @Override
    public <R> R getById(UUID id, Class<R> classType) {

        Question question = questionRepository.getById(id);

        // check permission
        if (!Context.hasModifyPermission() && !ValidateResource.isOwnResource(question, Question.class)) {
            throw new NoPermissionException("No permission");
        }

        return ObjectData.mapTo(question, classType);
    }

    @Override
    public QuestionDto create(CreateQuestionCommand command) {

        Question question = ObjectData.mapTo(command, Question.class);

        List<Answer> answers = getAnswers(command.getAnswerIds());

        question.setAnswers(answers);
        question.setUser(Context.getUser());

        question = questionRepository.save(question);

        return ObjectData.mapTo(question, QuestionDto.class);
    }

    @Override
    public QuestionDto update(UUID id, CreateQuestionCommand command) {

        Question question = getById(id, Question.class);

        if (Objects.nonNull(command.getContent())) {
            question.setContent(command.getContent());
        }

        if (Objects.nonNull(command.getAnswerIds())) {
            List<Answer> answers = getAnswers(command.getAnswerIds());

            question.setAnswers(answers);
        }

        question = questionRepository.save(question);
        return ObjectData.mapTo(question, QuestionDto.class);
    }

    @Override
    public void delete(UUID id, boolean force) {

        if (force) questionRepository.deleteById(id);
    }

    @Override
    public Page<QuestionDto> getAll(QuestionFilter filter, Pageable pageable) {
        Specification<Question> specification = QuestionSpecifications.withFilter(filter);
        Page<Question> questionPage = questionRepository.findAll(specification, pageable);
        return questionPage.map(q -> ObjectData.mapTo(q, QuestionDto.class));
    }

    @Override
    public List<QuestionDto> getAll(QuestionFilter filter) {
        Specification<Question> specification = QuestionSpecifications.withFilter(filter);
        return ObjectData.mapListTo(questionRepository.findAll(specification), QuestionDto.class);
    }

    private List<Answer> getAnswers(List<UUID> ids) {
        AnswerFilter answerFilter = new AnswerFilter();
        answerFilter.setInIds(ids);

        Specification<Answer> answerSpecification = AnswerSpecifications.withFilter(answerFilter);
        return answerRepository.findAll(answerSpecification);
    }
}
