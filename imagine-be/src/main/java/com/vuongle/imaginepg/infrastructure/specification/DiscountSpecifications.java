package com.vuongle.imaginepg.infrastructure.specification;

import com.vuongle.imaginepg.application.queries.BookFilter;
import com.vuongle.imaginepg.application.queries.DiscountFilter;
import com.vuongle.imaginepg.domain.entities.store.Book;
import com.vuongle.imaginepg.domain.entities.store.Discount;
import com.vuongle.imaginepg.shared.utils.SqlUtil;
import jakarta.persistence.criteria.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DiscountSpecifications {

    public static Specification<Discount> withFilter(DiscountFilter filter) {

        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.isNotBlank(filter.getLikeName())) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), SqlUtil.getLikePattern(filter.getLikeName())));
            }

            if (filter.getValueFrom() > 0) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("value"), filter.getValueFrom()));
            }

            if (Objects.nonNull(filter.getUnit())) {
                predicates.add(criteriaBuilder.equal(root.get("unit"), filter.getUnit()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}
