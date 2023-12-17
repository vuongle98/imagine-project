package com.vuongle.imaginepg.infrastructure.specification;

import com.vuongle.imaginepg.application.queries.TagFilter;
import com.vuongle.imaginepg.domain.entities.Tag;
import com.vuongle.imaginepg.shared.utils.SqlUtil;
import jakarta.persistence.criteria.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class TagSpecifications {

    public static Specification<Tag> withFilter(TagFilter filter) {

        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.isNotBlank(filter.getLikeName())) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), SqlUtil.getLikePattern(filter.getLikeName())));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private static Specification<Tag> likeName(String content) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), SqlUtil.getLikePattern(content));
    }
}
