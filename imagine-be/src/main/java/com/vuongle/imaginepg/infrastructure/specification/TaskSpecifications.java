package com.vuongle.imaginepg.infrastructure.specification;

import com.vuongle.imaginepg.application.queries.TaskFilter;
import com.vuongle.imaginepg.domain.constants.TaskColor;
import com.vuongle.imaginepg.domain.entities.Task;
import com.vuongle.imaginepg.shared.utils.SqlUtil;
import jakarta.persistence.criteria.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class TaskSpecifications {

    public static Specification<Task> withFilter(TaskFilter filter) {

        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.isNotBlank(filter.getLikeDescription())) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), SqlUtil.getLikePattern(filter.getLikeDescription())));
            }

            if (Objects.nonNull(filter.getColor())) {
                predicates.add(criteriaBuilder.equal(root.get("color"), filter.getColor()));
            }

            if (Objects.nonNull(filter.getUserId())) {
                predicates.add(criteriaBuilder.equal(root.get("user_id"), filter.getUserId()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
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
