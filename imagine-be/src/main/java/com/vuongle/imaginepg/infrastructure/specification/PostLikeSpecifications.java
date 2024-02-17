package com.vuongle.imaginepg.infrastructure.specification;

import com.vuongle.imaginepg.application.queries.PostLikeFilter;
import com.vuongle.imaginepg.domain.entities.PostLike;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PostLikeSpecifications {

    public static Specification<PostLike> withFilter(PostLikeFilter filter) {
        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (Objects.nonNull(filter.getPostId())) {
                predicates.add(criteriaBuilder.equal(root.get("post").get("id"), filter.getPostId()));
            }

            if (Objects.nonNull(filter.getUserId())) {
                predicates.add(criteriaBuilder.equal(root.get("user").get("id"), filter.getUserId()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
