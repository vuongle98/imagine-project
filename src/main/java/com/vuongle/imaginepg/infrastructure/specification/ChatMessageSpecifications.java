package com.vuongle.imaginepg.infrastructure.specification;

import com.vuongle.imaginepg.application.queries.ChatMessageFilter;
import com.vuongle.imaginepg.application.queries.CommentFilter;
import com.vuongle.imaginepg.domain.entities.Answer;
import com.vuongle.imaginepg.domain.entities.ChatMessage;
import com.vuongle.imaginepg.domain.entities.Comment;
import com.vuongle.imaginepg.shared.utils.SqlUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.Objects;
import java.util.UUID;

public class ChatMessageSpecifications {

    public static Specification<ChatMessage> withFilter(ChatMessageFilter filter) {

        Specification<ChatMessage> specification = Specification.where(null);

        if (StringUtils.isNotBlank(filter.getLikeContent())) {
            specification.and(likeContent(filter.getLikeContent()));
        }

        if (Objects.nonNull(filter.getId())) {
            specification.and(isId(filter.getId()));
        }

        if (Objects.nonNull(filter.getUserId())) {
            specification.and(isUserId(filter.getUserId()));
        }

        return specification;
    }

    private static Specification<ChatMessage> likeContent(String content) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("content")), SqlUtil.getLikePattern(content));
    }

    private static Specification<ChatMessage> isId(UUID id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), id);
    }

    private static Specification<ChatMessage> isUserId(UUID id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("user_id"), id);
    }
}
