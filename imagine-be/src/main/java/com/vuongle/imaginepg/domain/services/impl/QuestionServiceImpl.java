package com.vuongle.imaginepg.domain.services.impl;

import com.vuongle.imaginepg.application.commands.CreateQuestionCommand;
import com.vuongle.imaginepg.application.dto.QuestionDto;
import com.vuongle.imaginepg.application.exceptions.DataNotValidException;
import com.vuongle.imaginepg.application.exceptions.NoPermissionException;
import com.vuongle.imaginepg.application.queries.AnswerFilter;
import com.vuongle.imaginepg.application.queries.QuestionFilter;
import com.vuongle.imaginepg.domain.constants.QuestionType;
import com.vuongle.imaginepg.domain.entities.Answer;
import com.vuongle.imaginepg.domain.entities.Question;
import com.vuongle.imaginepg.domain.repositories.BaseQueryRepository;
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

    private final BaseQueryRepository<Question> questionQueryRepository;
    private final BaseRepository<Question> questionRepository;

    private final BaseQueryRepository<Answer> answerRepository;


    public QuestionServiceImpl(
            BaseQueryRepository<Question> questionQueryRepository,
            BaseRepository<Question> questionRepository,
            BaseQueryRepository<Answer> answerRepository
    ) {
        this.questionQueryRepository = questionQueryRepository;
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    @Override
    public QuestionDto getById(UUID id) {
        return getById(id, QuestionDto.class);
    }

    @Override
    public <R> R getById(UUID id, Class<R> classType) {

        Question question = questionQueryRepository.getById(id);

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

        if (question.getType().equals(QuestionType.YES_NO)) {
            if (answers.stream().filter(Answer::isCorrect).count() > 1) {
                throw new DataNotValidException("YES NO question can not has more than 1 correct answer");
            }
        }

        question.setAnswers(answers);
        question.setUser(Context.getUser());

        question = questionRepository.save(question);

        return ObjectData.mapTo(question, QuestionDto.class);
    }

    @Override
    public QuestionDto update(UUID id, CreateQuestionCommand command) {

        Question question = getById(id, Question.class);

        if (Objects.nonNull(command.getTitle())) {
            question.setTitle(command.getTitle());
        }

        if (Objects.nonNull(command.getLevel())) {
            question.setLevel(command.getLevel());
        }

        if (Objects.nonNull(command.getCategory())) {
            question.setCategory(command.getCategory());
        }

        if (Objects.nonNull(command.getType())) {
            question.setType(command.getType());
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
    public Page<QuestionDto> getPageable(QuestionFilter filter, Pageable pageable) {
        Specification<Question> specification = QuestionSpecifications.withFilter(filter);
        Page<Question> questionPage = questionQueryRepository.findAll(specification, pageable);
        return questionPage.map(q -> ObjectData.mapTo(q, QuestionDto.class));
    }

    @Override
    public List<QuestionDto> getList(QuestionFilter filter) {
        Specification<Question> specification = QuestionSpecifications.withFilter(filter);
        return ObjectData.mapListTo(questionQueryRepository.findAll(specification), QuestionDto.class);
    }

    private List<Answer> getAnswers(List<UUID> ids) {
        AnswerFilter answerFilter = new AnswerFilter();
        answerFilter.setInIds(ids);

        Specification<Answer> answerSpecification = AnswerSpecifications.withFilter(answerFilter);
        return answerRepository.findAll(answerSpecification);
    }
}
