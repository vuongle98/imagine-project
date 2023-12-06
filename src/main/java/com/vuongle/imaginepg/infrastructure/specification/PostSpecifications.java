package com.vuongle.imaginepg.infrastructure.specification;

import com.vuongle.imaginepg.application.queries.PostFilter;
import com.vuongle.imaginepg.domain.entities.Post;
import com.vuongle.imaginepg.shared.utils.SqlUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

public class PostSpecifications {

    public static Specification<Post> withFilter(PostFilter postFilter) {

        Specification<Post> specification = Specification.where(null);

        if (StringUtils.isNotBlank(postFilter.getLikeTitle())) {
            specification.and(likeTitle(postFilter.getLikeTitle()));
        }

        if (StringUtils.isNotBlank(postFilter.getLikeContent())) {
            specification.and(likeContent(postFilter.getLikeContent()));
        }

        return specification;

//        return (root, query, criteriaBuilder) -> {
//            List<Predicate> predicates = new ArrayList<>();
//
//            if (StringUtils.isNotBlank(title)) {
//                predicates.add(criteriaBuilder.like(
//                        criteriaBuilder.lower(root.get("title")),
//                        getLikePattern(title)
//                ));
//            }
//
//            if (StringUtils.isNotBlank(content)) {
//                predicates.add(criteriaBuilder.like(
//                        criteriaBuilder.lower(root.get("content")),
//                        getLikePattern(content)
//                ));
//            }
//
//            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
//        };
    }

    private static Specification<Post> likeTitle(String title) {
        return (root, query, criteriaBuilder) ->
            criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), SqlUtil.getLikePattern(title));
    }

    private static Specification<Post> likeContent(String content) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("content")), SqlUtil.getLikePattern(content));
    }
}
