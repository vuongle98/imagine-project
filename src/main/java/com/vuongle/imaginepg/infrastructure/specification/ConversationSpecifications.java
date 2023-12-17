package com.vuongle.imaginepg.infrastructure.specification;

import com.vuongle.imaginepg.application.queries.ConversationFilter;
import com.vuongle.imaginepg.domain.constants.ChatType;
import com.vuongle.imaginepg.domain.entities.Conversation;
import com.vuongle.imaginepg.domain.entities.User;
import com.vuongle.imaginepg.shared.utils.SqlUtil;
import jakarta.persistence.criteria.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class ConversationSpecifications {

    public static Specification<Conversation> withFilter(ConversationFilter filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.isNotBlank(filter.getLikeTitle())) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), SqlUtil.getLikePattern(filter.getLikeTitle())));
            }

            if (Objects.nonNull(filter.getId())) {
                predicates.add(criteriaBuilder.equal(root.get("id"), filter.getId()));
            }

            if (Objects.nonNull(filter.getUserId())) {
                predicates.add(criteriaBuilder.equal(root.get("user_id"), filter.getUserId()));
            }

            if (Objects.nonNull(filter.getEqualParticipantIds())) {

                Predicate[] userIn = filter.getEqualParticipantIds().stream().map(
                        userId -> createUserPredicate(root, criteriaBuilder, userId)
                ).toArray(Predicate[]::new);

                predicates.add(criteriaBuilder.and(userIn));
            }

            if (Objects.nonNull(filter.getInParticipantIds())) {
                Join<Conversation, User> participantsJoin = root.join("participants", JoinType.INNER);
                predicates.add(participantsJoin.get("id").in(filter.getInParticipantIds()));
            }

            if (Objects.nonNull(filter.getType())) {
                predicates.add(criteriaBuilder.equal(root.get("type"), filter.getType()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private static Predicate createUserPredicate(Root<Conversation> root, CriteriaBuilder criteriaBuilder, UUID userId) {
        Join<Conversation, User> participantsJoin = root.join("participants", JoinType.INNER);
        return criteriaBuilder.equal(participantsJoin.get("id"), userId);
    }

    public static Specification<Conversation> likeTitle(String title) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), SqlUtil.getLikePattern(title));
    }

    public static Specification<Conversation> isId(UUID id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), id);
    }

    public static Specification<Conversation> isUserId(UUID id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("user_id"), id);
    }

    public static Specification<Conversation> participantIds(List<UUID> ids) {
        return (root, query, criteriaBuilder) -> root.get("participants").get("id").in(ids);
    }

    public static Specification<Conversation> isGroupChat(boolean isGroupChat) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("isGroupChat"), isGroupChat);
    }

    public static Specification<Conversation> isType(ChatType type) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("type"), type);
    }
}
