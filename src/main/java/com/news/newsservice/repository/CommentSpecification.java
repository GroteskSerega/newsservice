package com.news.newsservice.repository;

import com.news.newsservice.entity.Comment;
import com.news.newsservice.web.dto.v1.CommentFilter;
import jakarta.persistence.criteria.Expression;
import org.springframework.data.jpa.domain.Specification;

import java.text.MessageFormat;
import java.time.Instant;

public interface CommentSpecification {

    String TEMPLATE_LIKE = "%{0}%";

    static Specification<Comment> withFilter(CommentFilter commentFilter) {
        return Specification.where(byMessage(commentFilter.getMessage()))
                .and(byUserId(commentFilter.getUserId()))
                .and(byNewsId(commentFilter.getNewsId()))
                .and(byCreateAtBefore(commentFilter.getCreateBefore()))
                .and(byUpdateAtBefore(commentFilter.getUpdateBefore()))
                .and(byCreateAtAfter(commentFilter.getCreateAfter()))
                .and(byUpdateAtAfter(commentFilter.getUpdateAfter()));
    }

    static Specification<Comment> byMessage(String message) {
        return (root, query, criteriaBuilder) -> {
            if (message == null) {
                return null;
            }

            String pattern = MessageFormat.format(TEMPLATE_LIKE, message.toLowerCase());

            Expression<String> lowerCaseField = criteriaBuilder.lower(root.get("message"));

            return criteriaBuilder.like(lowerCaseField, pattern);
        };
    }

    static Specification<Comment> byUserId(Long userId) {
        return (root, query, criteriaBuilder) -> {
            if (userId == null) {
                return null;
            }

            return criteriaBuilder.equal(root.get("user").get("id"), userId);
        };
    }

    static Specification<Comment> byNewsId(Long newsId) {
        return (root, query, criteriaBuilder) -> {
            if (newsId == null) {
                return null;
            }

            return criteriaBuilder.equal(root.get("news").get("id"), newsId);
        };
    }

    static Specification<Comment> byCreateAtBefore(Instant createBefore) {
        return (root, query, criteriaBuilder) -> {
            if (createBefore == null) {
                return null;
            }

            return criteriaBuilder.lessThanOrEqualTo(root.get("createAt"), createBefore);
        };
    }

    static Specification<Comment> byUpdateAtBefore(Instant updateBefore) {
        return (root, query, criteriaBuilder) -> {
            if (updateBefore == null) {
                return null;
            }

            return criteriaBuilder.lessThanOrEqualTo(root.get("updateAt"), updateBefore);
        };
    }

    static Specification<Comment> byCreateAtAfter(Instant createAfter) {
        return (root, query, criteriaBuilder) -> {
            if (createAfter == null) {
                return null;
            }

            return criteriaBuilder.greaterThanOrEqualTo(root.get("createAt"), createAfter);
        };
    }

    static Specification<Comment> byUpdateAtAfter(Instant updateAfter) {
        return (root, query, criteriaBuilder) -> {
            if (updateAfter == null) {
                return null;
            }

            return criteriaBuilder.greaterThanOrEqualTo(root.get("updateAt"), updateAfter);
        };
    }
}
