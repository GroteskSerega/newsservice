package com.news.newsservice.repository;

import com.news.newsservice.entity.Category;
import com.news.newsservice.web.dto.v1.CategoryFilter;
import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;

public interface CategorySpecification {

    static Specification<Category> withFilter(CategoryFilter categoryFilter) {

        return Specification.where(byCategory(categoryFilter.getCategory()))
                .and(byCreateAtBefore(categoryFilter.getCreateBefore()))
                .and(byUpdateAtBefore(categoryFilter.getUpdateBefore()))
                .and(byCreateAtAfter(categoryFilter.getCreateAfter()))
                .and(byUpdateAtAfter(categoryFilter.getUpdateAfter()));
    }

    static Specification<Category> byCategory(String category) {
        return (root, query, criteriaBuilder) -> {
            if (category == null) {
                return null;
            }

            return criteriaBuilder.equal(root.get("category"), category);
        };
    }

    static Specification<Category> byCreateAtBefore(Instant createBefore) {
        return (root, query, criteriaBuilder) -> {
            if (createBefore == null) {
                return null;
            }

            return criteriaBuilder.lessThanOrEqualTo(root.get("createAt"), createBefore);
        };
    }

    static Specification<Category> byUpdateAtBefore(Instant updateBefore) {
        return (root, query, criteriaBuilder) -> {
            if (updateBefore == null) {
                return null;
            }

            return criteriaBuilder.lessThanOrEqualTo(root.get("updateAt"), updateBefore);
        };
    }

    static Specification<Category> byCreateAtAfter(Instant createAfter) {
        return (root, query, criteriaBuilder) -> {
            if (createAfter == null) {
                return null;
            }

            return criteriaBuilder.greaterThanOrEqualTo(root.get("createAt"), createAfter);
        };
    }

    static Specification<Category> byUpdateAtAfter(Instant updateAfter) {
        return (root, query, criteriaBuilder) -> {
            if (updateAfter == null) {
                return null;
            }

            return criteriaBuilder.greaterThanOrEqualTo(root.get("updateAt"), updateAfter);
        };
    }
}
