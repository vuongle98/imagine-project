package com.vuongle.imaginepg.domain.services.impl;

import com.vuongle.imaginepg.application.commands.CreateQuizCommand;
import com.vuongle.imaginepg.application.dto.QuizDto;
import com.vuongle.imaginepg.application.queries.QuestionFilter;
import com.vuongle.imaginepg.application.queries.QuizFilter;
import com.vuongle.imaginepg.domain.entities.Question;
import com.vuongle.imaginepg.domain.entities.Quiz;
import com.vuongle.imaginepg.domain.repositories.BaseRepository;
import com.vuongle.imaginepg.domain.services.QuizService;
import com.vuongle.imaginepg.infrastructure.specification.QuestionSpecifications;
import com.vuongle.imaginepg.infrastructure.specification.QuizSpecifications;
import com.vuongle.imaginepg.shared.utils.Context;
import com.vuongle.imaginepg.shared.utils.ObjectData;
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
public class QuizServiceImpl implements QuizService {

    private final BaseRepository<Quiz> quizRepository;

    private final BaseRepository<Question> questionRepository;

    public QuizServiceImpl(
            BaseRepository<Quiz> quizRepository,
            BaseRepository<Question> questionRepository
    ) {
        this.quizRepository = quizRepository;
        this.questionRepository = questionRepository;
    }

    @Override
    public QuizDto getById(UUID id) {
        return ObjectData.mapTo(quizRepository.getById(id), QuizDto.class);
    }

    @Override
    public QuizDto create(CreateQuizCommand command) {

        Quiz quiz = ObjectData.mapTo(command, Quiz.class);

        quiz.setUser(Context.getUser());

        quiz.setQuestions(getQuestions(command.getAddQuestionIds()));

        quiz = quizRepository.save(quiz);
        return ObjectData.mapTo(quiz, QuizDto.class);
    }

    @Override
    public QuizDto update(UUID id, CreateQuizCommand command) {

        Quiz quiz = quizRepository.getById(id);

        if (Objects.nonNull(command.getAddQuestionIds())) {
            quiz.addQuestions(getQuestions(command.getAddQuestionIds()));
        }

        if (Objects.nonNull(command.getRemoveQuestionIds())) {
            quiz.removeQuestions(command.getRemoveQuestionIds());
        }

        if (Objects.nonNull(command.getTitle())) {
            quiz.setTitle(command.getTitle());
        }

        quiz = quizRepository.save(quiz);

        return ObjectData.mapTo(quiz, QuizDto.class);
    }

    @Override
    public void delete(UUID id, boolean force) {

        if (force) quizRepository.deleteById(id);
    }

    @Override
    public Page<QuizDto> getAll(QuizFilter filter, Pageable pageable) {
        Specification<Quiz> specification = QuizSpecifications.withFilter(filter);

        Page<Quiz> quizPage = quizRepository.findAll(specification, pageable);
        return quizPage.map(q -> ObjectData.mapTo(q, QuizDto.class));
    }

    @Override
    public List<QuizDto> getAll(QuizFilter filter) {

        Specification<Quiz> specification = QuizSpecifications.withFilter(filter);

        return ObjectData.mapListTo(quizRepository.findAll(specification), QuizDto.class);
    }

    private List<Question> getQuestions(List<UUID> ids) {
        QuestionFilter questionFilter = new QuestionFilter();
        questionFilter.setInIds(ids);

        Specification<Question> specification = QuestionSpecifications.withFilter(questionFilter);

        return questionRepository.findAll(specification);
    }
}
