package com.vuongle.imaginepg.infrastructure.specification;

import com.vuongle.imaginepg.application.queries.UserConversationFilter;
import com.vuongle.imaginepg.domain.entities.UserConversation;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserConversationSpecifications {

    public static Specification<UserConversation> withFilter(UserConversationFilter filter) {

        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (Objects.nonNull(filter.getConversationId())) {
                predicates.add(criteriaBuilder.equal(root.get("conversation").get("id"), filter.getConversationId()));
            }

            if (Objects.nonNull(filter.getUserId())) {
                predicates.add(criteriaBuilder.equal(root.get("user").get("id"), filter.getUserId()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}
