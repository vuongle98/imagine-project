package com.vuongle.imaginepg.infrastructure.specification;

import com.vuongle.imaginepg.application.queries.TaskFilter;
import com.vuongle.imaginepg.domain.constants.TaskColor;
import com.vuongle.imaginepg.domain.entities.Task;
import com.vuongle.imaginepg.shared.utils.SqlUtil;
import org.springframework.data.jpa.domain.Specification;

import java.util.Objects;
import java.util.UUID;

public class TaskSpecifications {

    public static Specification<Task> withFilter(TaskFilter filter) {
        Specification<Task> specification = Specification.where(null);

        if (Objects.nonNull(filter.getLikeDescription())) {
            specification.and(likeDescription(filter.getLikeDescription()));
        }

        if (Objects.nonNull(filter.getColor())) {
            specification.and(isColor(filter.getColor()));
        }

        if (Objects.nonNull(filter.getUserId())) {
            specification.and(withOwner(filter.getUserId()));
        }

        return specification;
    }

    private static Specification<Task> likeDescription(String desc) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), SqlUtil.getLikePattern(desc));
    }

    private static Specification<Task> isColor(TaskColor color) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("color"), color);
    }

    private static Specification<Task> withOwner(UUID userId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("user_id"), userId);
    }
}
