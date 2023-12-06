package com.vuongle.imaginepg.infrastructure.specification;

import com.vuongle.imaginepg.application.queries.CategoryFilter;
import com.vuongle.imaginepg.domain.entities.Category;
import com.vuongle.imaginepg.shared.utils.SqlUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

public class CategorySpecifications {

    public static Specification<Category> withFilter(CategoryFilter categoryFilter) {

        Specification<Category> specification = Specification.where(null);

        if (StringUtils.isNotBlank(categoryFilter.getLikeName())) {
            specification.and(likeName(categoryFilter.getLikeName()));
        }

        return specification;
    }

    private static Specification<Category> likeName(String name) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), SqlUtil.getLikePattern(name)));
    }
}
