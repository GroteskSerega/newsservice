package com.news.newsservice.repository;

import com.news.newsservice.entity.News;
import com.news.newsservice.web.dto.v1.NewsFilter;
import jakarta.persistence.criteria.Expression;
import org.springframework.data.jpa.domain.Specification;

import java.text.MessageFormat;
import java.time.Instant;

public interface NewsSpecification {

    String TEMPLATE_LIKE = "%{0}%";

    static Specification<News> withFilter(NewsFilter newsFilter) {
        return Specification.where(byText(newsFilter.getText()))
                .and(byUserId(newsFilter.getUserId()))
                .and(byCategoryId(newsFilter.getCategoryId()))
                .and(byCreateAtBefore(newsFilter.getCreateBefore()))
                .and(byUpdateAtBefore(newsFilter.getUpdateBefore()))
                .and(byCreateAtAfter(newsFilter.getCreateAfter()))
                .and(byUpdateAtAfter(newsFilter.getUpdateAfter()));
    }

    static Specification<News> byText(String text) {
        return (root, query, criteriaBuilder) -> {
            if (text == null) {
                return null;
            }

            String pattern = MessageFormat.format(TEMPLATE_LIKE, text.toLowerCase());

            Expression<String> lowerCaseField = criteriaBuilder.lower(root.get("text"));

            return criteriaBuilder.like(lowerCaseField, pattern);
        };
    }

    static Specification<News> byUserId(Long userId) {
        return (root, query, criteriaBuilder) -> {
            if (userId == null) {
                return null;
            }

            return criteriaBuilder.equal(root.get("user").get("id"), userId);
        };
    }

    static Specification<News> byCategoryId(Long categoryId) {
        return (root, query, criteriaBuilder) -> {
            if (categoryId == null) {
                return null;
            }

            return criteriaBuilder.equal(root.get("category").get("id"), categoryId);
        };
    }

    static Specification<News> byCreateAtBefore(Instant createBefore) {
        return (root, query, criteriaBuilder) -> {
            if (createBefore == null) {
                return null;
            }

            return criteriaBuilder.lessThanOrEqualTo(root.get("createAt"), createBefore);
        };
    }

    static Specification<News> byUpdateAtBefore(Instant updateBefore) {
        return (root, query, criteriaBuilder) -> {
            if (updateBefore == null) {
                return null;
            }

            return criteriaBuilder.lessThanOrEqualTo(root.get("updateAt"), updateBefore);
        };
    }

    static Specification<News> byCreateAtAfter(Instant createAfter) {
        return (root, query, criteriaBuilder) -> {
            if (createAfter == null) {
                return null;
            }

            return criteriaBuilder.greaterThanOrEqualTo(root.get("createAt"), createAfter);
        };
    }

    static Specification<News> byUpdateAtAfter(Instant updateAfter) {
        return (root, query, criteriaBuilder) -> {
            if (updateAfter == null) {
                return null;
            }

            return criteriaBuilder.greaterThanOrEqualTo(root.get("updateAt"), updateAfter);
        };
    }
}
