package com.vuongle.imaginepg.infrastructure.specification;

import com.vuongle.imaginepg.application.queries.CategoryFilter;
import com.vuongle.imaginepg.domain.entities.Category;
import com.vuongle.imaginepg.shared.utils.SqlUtil;
import jakarta.persistence.criteria.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CategorySpecifications {

    public static Specification<Category> withFilter(CategoryFilter categoryFilter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.isNotBlank(categoryFilter.getLikeName())) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), SqlUtil.getLikePattern(categoryFilter.getLikeName())));
            }

            if (Objects.nonNull(categoryFilter.getType())) {
                predicates.add(criteriaBuilder.equal(root.get("type"), categoryFilter.getType()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<Category> likeName(String name) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), SqlUtil.getLikePattern(name)));
    }
}
