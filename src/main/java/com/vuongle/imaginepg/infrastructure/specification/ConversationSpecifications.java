package com.vuongle.imaginepg.infrastructure.specification;

import com.vuongle.imaginepg.application.queries.ConversationFilter;
import com.vuongle.imaginepg.domain.entities.Conversation;
import com.vuongle.imaginepg.shared.utils.SqlUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.Objects;
import java.util.UUID;

public class ConversationSpecifications {

    public static Specification<Conversation> withFilter(ConversationFilter filter) {

        Specification<Conversation> specification = Specification.where(null);

        if (StringUtils.isNotBlank(filter.getLikeTitle())) {
            specification.and(likeTitle(filter.getLikeTitle()));
        }

        if (Objects.nonNull(filter.getId())) {
            specification.and(isId(filter.getId()));
        }

        if (Objects.nonNull(filter.getUserId())) {
            specification.and(isUserId(filter.getUserId()));
        }

        return specification;
    }

    private static Specification<Conversation> likeTitle(String title) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), SqlUtil.getLikePattern(title));
    }

    private static Specification<Conversation> isId(UUID id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), id);
    }

    private static Specification<Conversation> isUserId(UUID id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("user_id"), id);
    }
}
