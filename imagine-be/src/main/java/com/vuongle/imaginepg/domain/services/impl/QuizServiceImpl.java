package com.vuongle.imaginepg.domain.services.impl;

import com.vuongle.imaginepg.application.commands.CreateQuizCommand;
import com.vuongle.imaginepg.application.commands.SubmitAnswer;
import com.vuongle.imaginepg.application.dto.AnswerQuizResult;
import com.vuongle.imaginepg.application.dto.QuestionDto;
import com.vuongle.imaginepg.application.dto.QuizDto;
import com.vuongle.imaginepg.application.exceptions.DataNotValidException;
import com.vuongle.imaginepg.application.exceptions.NoPermissionException;
import com.vuongle.imaginepg.application.queries.QuestionFilter;
import com.vuongle.imaginepg.application.queries.QuizFilter;
import com.vuongle.imaginepg.domain.entities.Answer;
import com.vuongle.imaginepg.domain.entities.File;
import com.vuongle.imaginepg.domain.entities.Question;
import com.vuongle.imaginepg.domain.entities.Quiz;
import com.vuongle.imaginepg.domain.repositories.BaseQueryRepository;
import com.vuongle.imaginepg.domain.repositories.BaseRepository;
import com.vuongle.imaginepg.domain.services.FileService;
import com.vuongle.imaginepg.domain.services.QuizService;
import com.vuongle.imaginepg.infrastructure.specification.QuestionSpecifications;
import com.vuongle.imaginepg.infrastructure.specification.QuizSpecifications;
import com.vuongle.imaginepg.shared.utils.Context;
import com.vuongle.imaginepg.shared.utils.ObjectData;
import com.vuongle.imaginepg.shared.utils.ValidateResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class QuizServiceImpl implements QuizService {

    private final BaseQueryRepository<Quiz> quizQueryRepository;
    private final BaseRepository<Quiz> quizRepository;

    private final BaseQueryRepository<Question> questionQueryRepository;

    private final FileService fileService;

    public QuizServiceImpl(
            BaseQueryRepository<Quiz> quizQueryRepository,
            BaseRepository<Quiz> quizRepository,
            BaseQueryRepository<Question> questionQueryRepository,
            FileService fileService
    ) {
        this.quizRepository = quizRepository;
        this.quizQueryRepository = quizQueryRepository;
        this.questionQueryRepository = questionQueryRepository;
        this.fileService = fileService;
    }

    @Override
    public QuizDto getById(UUID id) {
        return getById(id, QuizDto.class);
    }

    @Override
    public <R> R getById(UUID id, Class<R> classType) {

        Quiz quiz = quizQueryRepository.getById(id);

        // check permission
        if (Objects.isNull(quiz.getPublishedAt()) && !Context.hasModifyPermission() && !ValidateResource.isOwnResource(quiz, Quiz.class)) {
            throw new NoPermissionException("No permission");
        }

        return ObjectData.mapTo(quiz, classType);
    }

    @Override
    public QuizDto create(CreateQuizCommand command) {

        // validate no duplicate question

        Set<UUID> questionIds = new HashSet<>(command.getAddQuestionIds());

        if (command.getAddQuestionIds().size() > questionIds.size()) {
            throw new DataNotValidException("Contain duplicate questions");
        }

        Quiz quiz = ObjectData.mapTo(command, Quiz.class);

        quiz.setUser(Context.getUser());

        List<Question> questions = getQuestions(command.getAddQuestionIds());

        quiz.setQuestions(questions);

        quiz.setCoverImage(fileService.getById(command.getFileId(), File.class));

        quiz = quizRepository.save(quiz);
        return ObjectData.mapTo(quiz, QuizDto.class);
    }

    @Override
    public QuizDto update(UUID id, CreateQuizCommand command) {

        Set<UUID> questionIds = new HashSet<>(command.getAddQuestionIds());

        if (command.getAddQuestionIds().size() > questionIds.size()) {
            throw new DataNotValidException("Contain duplicate questions");
        }

        Quiz quiz = getById(id, Quiz.class);

        if (Objects.nonNull(command.getAddQuestionIds())) {
            quiz.addQuestions(getQuestions(command.getAddQuestionIds()));
        }

        if (Objects.nonNull(command.getRemoveQuestionIds())) {
            quiz.removeQuestions(command.getRemoveQuestionIds());
        }

        if (Objects.nonNull(command.getTitle())) {
            quiz.setTitle(command.getTitle());
        }

        if (Objects.nonNull(command.getFileId())) {
            quiz.setCoverImage(fileService.getById(command.getFileId(), File.class));
        }

        if (Objects.nonNull(command.getDescription())) {
            quiz.setDescription(command.getDescription());
        }

        if (Objects.nonNull(command.getLevel())) {
            quiz.setLevel(command.getLevel());
        }

        quiz = quizRepository.save(quiz);

        return ObjectData.mapTo(quiz, QuizDto.class);
    }

    @Override
    public void delete(UUID id, boolean force) {

        Quiz quiz = getById(id, Quiz.class);

        if (force) quizRepository.deleteById(id);
        else {
            quiz.setDeletedAt(Instant.now());
            quizRepository.save(quiz);
        }
    }

    @Override
    public Page<QuizDto> getPageable(QuizFilter filter, Pageable pageable) {
        Specification<Quiz> specification = QuizSpecifications.withFilter(filter);

        Page<Quiz> quizPage = quizQueryRepository.findAll(specification, pageable);
        return quizPage.map(q -> ObjectData.mapTo(q, QuizDto.class));
    }

    @Override
    public List<QuizDto> getList(QuizFilter filter) {

        Specification<Quiz> specification = QuizSpecifications.withFilter(filter);

        return ObjectData.mapListTo(quizQueryRepository.findAll(specification), QuizDto.class);
    }

    @Override
    public AnswerQuizResult answerQuiz(UUID quizId, List<SubmitAnswer> answers) {

        AnswerQuizResult result = new AnswerQuizResult();

        Quiz quiz = getById(quizId, Quiz.class);

        int totalCorrect = 0;

        for (SubmitAnswer answer : answers) {
            for (Question question : quiz.getQuestions()) {
                if (question.getId().equals(answer.getQuestionId())) {
                    int correctNum = question.checkAnswer(answer);
                    totalCorrect += correctNum;

                    answer.setCorrectAnswerIds(question.getAnswers().stream().filter(Answer::isCorrect).map(Answer::getId).collect(Collectors.toList()));
                    answer.setQuestion(ObjectData.mapTo(question, QuestionDto.class));
                }
            }
        }

        result.setTotalAnswers(answers.size());
        result.setNumOfCorrectAnswers(totalCorrect);
        result.setAnswers(answers);

        return result;
    }

    private List<Question> getQuestions(List<UUID> ids) {
        QuestionFilter questionFilter = new QuestionFilter();
        questionFilter.setInIds(ids);

        Specification<Question> specification = QuestionSpecifications.withFilter(questionFilter);

        return questionQueryRepository.findAll(specification);
    }
}
