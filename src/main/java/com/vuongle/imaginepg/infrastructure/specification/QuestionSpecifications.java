package com.vuongle.imaginepg.infrastructure.specification;

import com.vuongle.imaginepg.application.queries.CommentFilter;
import com.vuongle.imaginepg.application.queries.QuestionFilter;
import com.vuongle.imaginepg.domain.entities.Question;
import com.vuongle.imaginepg.shared.utils.ObjectData;
import com.vuongle.imaginepg.shared.utils.SqlUtil;
import jakarta.persistence.criteria.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class QuestionSpecifications {

    public static Specification<Question> withFilter(QuestionFilter filter) {

        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.isNotBlank(filter.getLikeContent())) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("content")), SqlUtil.getLikePattern(filter.getLikeContent())));
            }

            if (Objects.nonNull(filter.getLikeAnswer())) {
                predicates.add(criteriaBuilder.equal(root.get("answer").get("content"), SqlUtil.getLikePattern(filter.getLikeAnswer())));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private static Specification<Question> likeContent(String content) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("content")), SqlUtil.getLikePattern(content));
    }

    private static Specification<Question> likeAnswer(String answer) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("answer").get("content"), SqlUtil.getLikePattern(answer));
    }

    private static Specification<Question> isId(UUID id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), id);
    }

}
