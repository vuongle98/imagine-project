package com.vuongle.imaginepg.infrastructure.specification;

import com.vuongle.imaginepg.application.queries.AnswerFilter;
import com.vuongle.imaginepg.application.queries.BookFilter;
import com.vuongle.imaginepg.domain.entities.Answer;
import com.vuongle.imaginepg.domain.entities.store.Book;
import com.vuongle.imaginepg.shared.utils.SqlUtil;
import jakarta.persistence.criteria.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BookSpecifications {

    public static Specification<Book> withFilter(BookFilter filter) {

        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.isNotBlank(filter.getLikeTitle())) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), SqlUtil.getLikePattern(filter.getLikeTitle())));
            }

            if (Objects.nonNull(filter.getAuthorId())) {
                predicates.add(criteriaBuilder.equal(root.get("author").get("id"), filter.getAuthorId()));
            }

            if (filter.getPriceFrom() > 0) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), filter.getPriceFrom()));
            }

            if (Objects.nonNull(filter.getPublishedFrom())) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("publishedAt"), filter.getPublishedFrom()));
            }

            if (filter.getEdition() > 0) {
                predicates.add(criteriaBuilder.equal(root.get("edition"), filter.getEdition()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}
