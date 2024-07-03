package com.vuongle.imaginepg.infrastructure.specification;

import com.vuongle.imaginepg.application.queries.CommentFilter;
import com.vuongle.imaginepg.domain.entities.Comment;
import com.vuongle.imaginepg.shared.utils.SqlUtil;
import jakarta.persistence.criteria.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class CommentSpecifications {

    public static Specification<Comment> withFilter(CommentFilter filter) {

        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.isNotBlank(filter.getLikeContent())) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("content")), SqlUtil.getLikePattern(filter.getLikeContent())));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private static Specification<Comment> likeContent(String content) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("content")), SqlUtil.getLikePattern(content));
    }
}
