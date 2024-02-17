package com.vuongle.imaginepg.infrastructure.specification;

import com.vuongle.imaginepg.application.queries.QuizFilter;
import com.vuongle.imaginepg.domain.entities.Quiz;
import com.vuongle.imaginepg.shared.utils.SqlUtil;
import jakarta.persistence.criteria.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class QuizSpecifications {

    public static Specification<Quiz> withFilter(QuizFilter quizFilter) {

        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.isNotBlank(quizFilter.getLikeTitle())) {
                predicates.add(criteriaBuilder.equal(criteriaBuilder.lower(root.get("title")), SqlUtil.getLikePattern(quizFilter.getLikeTitle())));
            }

            if (StringUtils.isNotBlank(quizFilter.getLikeQuestion())) {
                predicates.add(criteriaBuilder.like(root.get("question").get("title"), SqlUtil.getLikePattern(quizFilter.getLikeQuestion())));
            }

            if (Objects.nonNull(quizFilter.getId())) {
                predicates.add(criteriaBuilder.equal(root.get("id"), quizFilter.getId()));
            }

            if (Objects.nonNull(quizFilter.getQuestionId())) {
                predicates.add(criteriaBuilder.equal(root.get("question").get("id"), quizFilter.getQuestionId()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
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
