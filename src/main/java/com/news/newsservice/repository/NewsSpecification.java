package com.news.newsservice.repository;

import com.news.newsservice.entity.Category_;
import com.news.newsservice.entity.News;
import com.news.newsservice.entity.News_;
import com.news.newsservice.entity.User_;
import com.news.newsservice.web.dto.v1.NewsFilter;
import jakarta.persistence.criteria.Expression;
import org.springframework.data.jpa.domain.Specification;

import java.text.MessageFormat;
import java.time.Instant;
import java.util.UUID;

public interface NewsSpecification {

    String TEMPLATE_LIKE = "%{0}%";

    static Specification<News> withFilter(NewsFilter newsFilter) {
        return Specification.allOf(byText(newsFilter.text()))
                .and(byUserId(newsFilter.userId()))
                .and(byCategoryId(newsFilter.categoryId()))
                .and(byCreateAtBefore(newsFilter.createBefore()))
                .and(byUpdateAtBefore(newsFilter.updateBefore()))
                .and(byCreateAtAfter(newsFilter.createAfter()))
                .and(byUpdateAtAfter(newsFilter.updateAfter()));
    }

    static Specification<News> byText(String text) {
        return (root, query, criteriaBuilder) -> {
            if (text == null) {
                return null;
            }

            String pattern = MessageFormat.format(TEMPLATE_LIKE, text.toLowerCase());

            Expression<String> lowerCaseField = criteriaBuilder.lower(root.get(News_.TEXT));

            return criteriaBuilder.like(lowerCaseField, pattern);
        };
    }

    static Specification<News> byUserId(UUID userId) {
        return (root, query, criteriaBuilder) -> {
            if (userId == null) {
                return null;
            }

            return criteriaBuilder.equal(root.get(News_.USER).get(User_.ID), userId);
        };
    }

    static Specification<News> byCategoryId(UUID categoryId) {
        return (root, query, criteriaBuilder) -> {
            if (categoryId == null) {
                return null;
            }

            return criteriaBuilder.equal(root.get(News_.CATEGORY).get(Category_.ID), categoryId);
        };
    }

    static Specification<News> byCreateAtBefore(Instant createBefore) {
        return (root, query, criteriaBuilder) -> {
            if (createBefore == null) {
                return null;
            }

            return criteriaBuilder.lessThanOrEqualTo(root.get(News_.CREATE_AT), createBefore);
        };
    }

    static Specification<News> byUpdateAtBefore(Instant updateBefore) {
        return (root, query, criteriaBuilder) -> {
            if (updateBefore == null) {
                return null;
            }

            return criteriaBuilder.lessThanOrEqualTo(root.get(News_.UPDATE_AT), updateBefore);
        };
    }

    static Specification<News> byCreateAtAfter(Instant createAfter) {
        return (root, query, criteriaBuilder) -> {
            if (createAfter == null) {
                return null;
            }

            return criteriaBuilder.greaterThanOrEqualTo(root.get(News_.CREATE_AT), createAfter);
        };
    }

    static Specification<News> byUpdateAtAfter(Instant updateAfter) {
        return (root, query, criteriaBuilder) -> {
            if (updateAfter == null) {
                return null;
            }

            return criteriaBuilder.greaterThanOrEqualTo(root.get(News_.UPDATE_AT), updateAfter);
        };
    }
}
