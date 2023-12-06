package com.vuongle.imaginepg.infrastructure.specification;

import com.vuongle.imaginepg.application.queries.CommentFilter;
import com.vuongle.imaginepg.domain.entities.Comment;
import com.vuongle.imaginepg.shared.utils.SqlUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

public class CommentSpecifications {

    public static Specification<Comment> withFilter(CommentFilter filter) {

        Specification<Comment> specification = Specification.where(null);

        if (StringUtils.isNotBlank(filter.getLikeContent())) {
            specification.and(likeContent(filter.getLikeContent()));
        }

        return specification;
    }

    private static Specification<Comment> likeContent(String content) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("content")), SqlUtil.getLikePattern(content));
    }
}
