package com.vuongle.imaginepg.infrastructure.specification;

import com.vuongle.imaginepg.application.queries.UserFilter;
import com.vuongle.imaginepg.domain.entities.User;
import com.vuongle.imaginepg.shared.utils.SqlUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class UserSpecifications {

    public static Specification<User> withFilter(UserFilter userFilter) {

        Specification<User> specification = Specification.where(null);

        if (StringUtils.isNotBlank(userFilter.getLikeUsername())) {
            specification.and(likeUserName(userFilter.getLikeUsername()));
        }

        if (StringUtils.isNotBlank(userFilter.getLikeEmail())) {
            specification.and(likeEmail(userFilter.getLikeEmail()));
        }

        if (StringUtils.isNotBlank(userFilter.getLikeFullName())) {
            specification.and(likeFullName(userFilter.getLikeFullName()));
        }

        if (Objects.nonNull(userFilter.getId())) {
            specification.and(isId(userFilter.getId()));
        }

        if (Objects.nonNull(userFilter.getInIds())) {
            specification.and(inIds(userFilter.getInIds()));
        }

        return specification;
    }

    public static Specification<User> likeUserName(String username) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("username")), SqlUtil.getLikePattern(username));
    }

    public static Specification<User> likeFullName(String fullName) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("username")), SqlUtil.getLikePattern(fullName));
    }

    public static Specification<User> likeEmail(String email) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), SqlUtil.getLikePattern(email));
    }

    public static Specification<User> inIds(List<UUID> ids) {
        return (root, query, criteriaBuilder) -> root.get("id").in(ids);
    }

    public static Specification<User> isId(UUID id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), id);
    }
}
