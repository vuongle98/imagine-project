package com.vuongle.imaginepg.infrastructure.specification;

import com.vuongle.imaginepg.application.queries.TagFilter;
import com.vuongle.imaginepg.domain.entities.Tag;
import com.vuongle.imaginepg.shared.utils.SqlUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

public class TagSpecifications {

    public static Specification<Tag> withFilter(TagFilter filter) {

        Specification<Tag> specification = Specification.where(null);

        if (StringUtils.isNotBlank(filter.getLikeName())) {
            specification.and(likeName(filter.getLikeName()));
        }

        return specification;
    }

    private static Specification<Tag> likeName(String content) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), SqlUtil.getLikePattern(content));
    }
}
