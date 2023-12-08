package com.vuongle.imaginepg.infrastructure.specification;

import com.vuongle.imaginepg.application.queries.CommentFilter;
import com.vuongle.imaginepg.application.queries.QuestionFilter;
import com.vuongle.imaginepg.domain.entities.Question;
import com.vuongle.imaginepg.shared.utils.ObjectData;
import com.vuongle.imaginepg.shared.utils.SqlUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.Objects;
import java.util.UUID;

public class QuestionSpecifications {

    public static Specification<Question> withFilter(QuestionFilter filter) {

        Specification<Question> specification = Specification.where(null);

        if (StringUtils.isNotBlank(filter.getLikeContent())) {
            specification.and(likeContent(filter.getLikeContent()));
        }

        if (Objects.nonNull(filter.getLikeAnswer())) {
            specification.and(likeAnswer(filter.getLikeAnswer()));
        }

        return specification;
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
