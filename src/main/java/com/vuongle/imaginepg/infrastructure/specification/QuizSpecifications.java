package com.vuongle.imaginepg.infrastructure.specification;

import com.vuongle.imaginepg.application.queries.QuizFilter;
import com.vuongle.imaginepg.domain.entities.Quiz;
import com.vuongle.imaginepg.shared.utils.SqlUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class QuizSpecifications {

    public static Specification<Quiz> withFilter(QuizFilter quizFilter) {
        Specification<Quiz> specification = Specification.where(null);

        if (StringUtils.isNotBlank(quizFilter.getLikeTitle())) {
            specification.and(likeTitle(quizFilter.getLikeTitle()));
        }

        if (StringUtils.isNotBlank(quizFilter.getLikeQuestion())) {
            specification.and(likeQuestion(quizFilter.getLikeQuestion()));
        }

        if (Objects.nonNull(quizFilter.getId())) {
            specification.and(isId(quizFilter.getId()));
        }

        if (Objects.nonNull(quizFilter.getQuestionId())) {
            specification.and(isQuestionId(quizFilter.getQuestionId()));
        }

        return specification;
    }

    private static Specification<Quiz> likeTitle(String title) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(criteriaBuilder.lower(root.get("title")), SqlUtil.getLikePattern(title));
    }

    private static Specification<Quiz> isId(UUID id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), id);
    }

    private static Specification<Quiz> inIds(List<UUID> ids) {
        return (root, query, criteriaBuilder) -> root.get("id").in(ids);
    }

    private static Specification<Quiz> likeQuestion(String question) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("question").get("title"), SqlUtil.getLikePattern(question));
    }

    private static Specification<Quiz> isQuestionId(UUID questionId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("question").get("id"), questionId);
    }
}
