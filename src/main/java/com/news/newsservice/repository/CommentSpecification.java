package com.news.newsservice.repository;

import com.news.newsservice.entity.Comment;
import com.news.newsservice.entity.Comment_;
import com.news.newsservice.entity.News_;
import com.news.newsservice.entity.User_;
import com.news.newsservice.web.dto.v1.CommentFilter;
import jakarta.persistence.criteria.Expression;
import org.springframework.data.jpa.domain.Specification;

import java.text.MessageFormat;
import java.time.Instant;
import java.util.UUID;

public interface CommentSpecification {

    String TEMPLATE_LIKE = "%{0}%";

    static Specification<Comment> withFilter(CommentFilter commentFilter) {
        return Specification.allOf(byMessage(commentFilter.message()))
                .and(byUserId(commentFilter.userId()))
                .and(byNewsId(commentFilter.newsId()))
                .and(byCreateAtBefore(commentFilter.createBefore()))
                .and(byUpdateAtBefore(commentFilter.updateBefore()))
                .and(byCreateAtAfter(commentFilter.createAfter()))
                .and(byUpdateAtAfter(commentFilter.updateAfter()));
    }

    static Specification<Comment> byMessage(String message) {
        return (root, query, criteriaBuilder) -> {
            if (message == null) {
                return null;
            }

            String pattern = MessageFormat.format(TEMPLATE_LIKE, message.toLowerCase());

            Expression<String> lowerCaseField = criteriaBuilder.lower(root.get(Comment_.MESSAGE));

            return criteriaBuilder.like(lowerCaseField, pattern);
        };
    }

    static Specification<Comment> byUserId(UUID userId) {
        return (root, query, criteriaBuilder) -> {
            if (userId == null) {
                return null;
            }

            return criteriaBuilder.equal(root.get(Comment_.USER).get(User_.ID), userId);
        };
    }

    static Specification<Comment> byNewsId(UUID newsId) {
        return (root, query, criteriaBuilder) -> {
            if (newsId == null) {
                return null;
            }

            return criteriaBuilder.equal(root.get(Comment_.NEWS).get(News_.ID), newsId);
        };
    }

    static Specification<Comment> byCreateAtBefore(Instant createBefore) {
        return (root, query, criteriaBuilder) -> {
            if (createBefore == null) {
                return null;
            }

            return criteriaBuilder.lessThanOrEqualTo(root.get(Comment_.CREATE_AT), createBefore);
        };
    }

    static Specification<Comment> byUpdateAtBefore(Instant updateBefore) {
        return (root, query, criteriaBuilder) -> {
            if (updateBefore == null) {
                return null;
            }

            return criteriaBuilder.lessThanOrEqualTo(root.get(Comment_.UPDATE_AT), updateBefore);
        };
    }

    static Specification<Comment> byCreateAtAfter(Instant createAfter) {
        return (root, query, criteriaBuilder) -> {
            if (createAfter == null) {
                return null;
            }

            return criteriaBuilder.greaterThanOrEqualTo(root.get(Comment_.CREATE_AT), createAfter);
        };
    }

    static Specification<Comment> byUpdateAtAfter(Instant updateAfter) {
        return (root, query, criteriaBuilder) -> {
            if (updateAfter == null) {
                return null;
            }

            return criteriaBuilder.greaterThanOrEqualTo(root.get(Comment_.UPDATE_AT), updateAfter);
        };
    }
}
