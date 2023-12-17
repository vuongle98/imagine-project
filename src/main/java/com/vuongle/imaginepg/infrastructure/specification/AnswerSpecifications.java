package com.vuongle.imaginepg.infrastructure.specification;

import com.vuongle.imaginepg.application.queries.AnswerFilter;
import com.vuongle.imaginepg.domain.entities.Answer;
import com.vuongle.imaginepg.shared.utils.SqlUtil;
import jakarta.persistence.criteria.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class AnswerSpecifications {

    public static Specification<Answer> withFilter(AnswerFilter filter) {

        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.isNotBlank(filter.getLikeContent())) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("content")), SqlUtil.getLikePattern(filter.getLikeContent())));
            }

            if (Objects.nonNull(filter.getId())) {
                predicates.add(criteriaBuilder.equal(root.get("id"), filter.getId()));
            }

            if (Objects.nonNull(filter.getInIds())) {
                predicates.add(root.get("id").in(filter.getInIds()));
            }

            if (Objects.nonNull(filter.getUserId())) {
                predicates.add(criteriaBuilder.equal(root.get("user_id"), filter.getUserId()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<Answer> likeContent(String content) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("content")), SqlUtil.getLikePattern(content));
    }

    public static Specification<Answer> isId(UUID id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), id);
    }

    public static Specification<Answer> inIds(List<UUID> ids) {
        return (root, query, criteriaBuilder) -> root.get("id").in(ids);
    }

    public static Specification<Answer> byOwner(UUID id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("user_id"), id);
    }
}
