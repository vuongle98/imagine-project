package com.vuongle.imaginepg.infrastructure.specification;

import com.vuongle.imaginepg.application.queries.AnswerFilter;
import com.vuongle.imaginepg.domain.entities.Answer;
import com.vuongle.imaginepg.shared.utils.SqlUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class AnswerSpecifications {

    public static Specification<Answer> withFilter(AnswerFilter filter) {

        Specification<Answer> specification = Specification.where(null);

        if (StringUtils.isNotBlank(filter.getLikeContent())) {
            specification.and(likeContent(filter.getLikeContent()));
        }

        if (Objects.nonNull(filter.getId())) {
            specification.and(isId(filter.getId()));
        }

        if (Objects.nonNull(filter.getInIds())) {
            specification.and(inIds(filter.getInIds()));
        }

        if (Objects.nonNull(filter.getUserId())) {
            specification.and(byOwner(filter.getUserId()));
        }

        return specification;
    }

    private static Specification<Answer> likeContent(String content) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("content")), SqlUtil.getLikePattern(content));
    }

    private static Specification<Answer> isId(UUID id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), id);
    }

    private static Specification<Answer> inIds(List<UUID> ids) {
        return (root, query, criteriaBuilder) -> root.get("id").in(ids);
    }

    private static Specification<Answer> byOwner(UUID id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("user_id"), id);
    }
}
