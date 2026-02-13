package com.news.newsservice.repository;

import com.news.newsservice.entity.Category;
import com.news.newsservice.entity.Category_;
import com.news.newsservice.web.dto.v1.CategoryFilter;
import jakarta.persistence.criteria.Expression;
import org.springframework.data.jpa.domain.Specification;

import java.text.MessageFormat;
import java.time.Instant;

public interface CategorySpecification {

    String TEMPLATE_LIKE = "%{0}%";

    static Specification<Category> withFilter(CategoryFilter categoryFilter) {

        return Specification.allOf(byCategory(categoryFilter.category()))
                .and(byCreateAtBefore(categoryFilter.createBefore()))
                .and(byUpdateAtBefore(categoryFilter.updateBefore()))
                .and(byCreateAtAfter(categoryFilter.createAfter()))
                .and(byUpdateAtAfter(categoryFilter.updateAfter()));
    }

    static Specification<Category> byCategory(String category) {
        return (root, query, criteriaBuilder) -> {
            if (category == null) {
                return null;
            }

            String pattern = MessageFormat.format(TEMPLATE_LIKE, category.toLowerCase());

            Expression<String> lowerCaseField = criteriaBuilder.lower(root.get(Category_.CATEGORY));

            return criteriaBuilder.like(lowerCaseField, pattern);
        };
    }

    static Specification<Category> byCreateAtBefore(Instant createBefore) {
        return (root, query, criteriaBuilder) -> {
            if (createBefore == null) {
                return null;
            }

            return criteriaBuilder.lessThanOrEqualTo(root.get(Category_.CREATE_AT), createBefore);
        };
    }

    static Specification<Category> byUpdateAtBefore(Instant updateBefore) {
        return (root, query, criteriaBuilder) -> {
            if (updateBefore == null) {
                return null;
            }

            return criteriaBuilder.lessThanOrEqualTo(root.get(Category_.UPDATE_AT), updateBefore);
        };
    }

    static Specification<Category> byCreateAtAfter(Instant createAfter) {
        return (root, query, criteriaBuilder) -> {
            if (createAfter == null) {
                return null;
            }

            return criteriaBuilder.greaterThanOrEqualTo(root.get(Category_.CREATE_AT), createAfter);
        };
    }

    static Specification<Category> byUpdateAtAfter(Instant updateAfter) {
        return (root, query, criteriaBuilder) -> {
            if (updateAfter == null) {
                return null;
            }

            return criteriaBuilder.greaterThanOrEqualTo(root.get(Category_.UPDATE_AT), updateAfter);
        };
    }
}
