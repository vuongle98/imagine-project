package com.vuongle.imaginepg.infrastructure.specification;

import com.vuongle.imaginepg.application.queries.PostFilter;
import com.vuongle.imaginepg.domain.entities.Post;
import com.vuongle.imaginepg.shared.utils.SqlUtil;
import jakarta.persistence.criteria.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class PostSpecifications {

    public static Specification<Post> withFilter(PostFilter postFilter) {

        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.isNotBlank(postFilter.getLikeTitle())) {
                predicates.add(criteriaBuilder.like(root.get("title"), SqlUtil.getLikePattern(postFilter.getLikeTitle())));
            }

            if (StringUtils.isNotBlank(postFilter.getLikeContent())) {
                predicates.add(criteriaBuilder.like(root.get("content"), SqlUtil.getLikePattern(postFilter.getLikeContent())));
            }

            if (Objects.nonNull(postFilter.getCategoryId())) {
                predicates.add(criteriaBuilder.equal(root.get("category_id"), postFilter.getCategoryId()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private static Specification<Post> likeTitle(String title) {
        return (root, query, criteriaBuilder) ->
            criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), SqlUtil.getLikePattern(title));
    }

    private static Specification<Post> likeContent(String content) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("content")), SqlUtil.getLikePattern(content));
    }

    private static Specification<Post> inCategoryId(UUID categoryId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("category_id"), categoryId);
    }
}
