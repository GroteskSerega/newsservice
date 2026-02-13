package com.news.newsservice.repository;

import com.news.newsservice.entity.User;
import com.news.newsservice.entity.User_;
import com.news.newsservice.web.dto.v1.UserFilter;
import jakarta.persistence.criteria.Expression;
import org.springframework.data.jpa.domain.Specification;

import java.text.MessageFormat;
import java.time.Instant;

public interface UserSpecification {

    String TEMPLATE_LIKE = "%{0}%";

    static Specification<User> withFilter(UserFilter userFilter) {

        return Specification.allOf(byUsername(userFilter.username()))
                .and(byFirstName(userFilter.firstName()))
                .and(bySecondName(userFilter.secondName()))
                .and(byLastName(userFilter.lastName()))
                .and(byEmail(userFilter.email()))
                .and(byCreateAtBefore(userFilter.createBefore()))
                .and(byUpdateAtBefore(userFilter.updateBefore()))
                .and(byCreateAtAfter(userFilter.createAfter()))
                .and(byUpdateAtAfter(userFilter.updateAfter()));
    }

    static Specification<User> byUsername(String username) {
        return (root, query, criteriaBuilder) -> {
            if (username == null) {
                return null;
            }

            return criteriaBuilder.equal(root.get(User_.USERNAME), username);
        };
    }

    static Specification<User> byFirstName(String firstName) {
        return (root, query, criteriaBuilder) -> {
            if (firstName == null) {
                return null;
            }

            String pattern = MessageFormat.format(TEMPLATE_LIKE, firstName.toLowerCase());

            Expression<String> lowerCaseField = criteriaBuilder.lower(root.get(User_.FIRST_NAME));

            return criteriaBuilder.like(lowerCaseField, pattern);
        };
    }

    static Specification<User> bySecondName(String secondName) {
        return (root, query, criteriaBuilder) -> {
            if (secondName == null) {
                return null;
            }

            String pattern = MessageFormat.format(TEMPLATE_LIKE, secondName.toLowerCase());

            Expression<String> lowerCaseField = criteriaBuilder.lower(root.get(User_.SECOND_NAME));

            return criteriaBuilder.like(lowerCaseField, pattern);
        };
    }

    static Specification<User> byLastName(String lastName) {
        return (root, query, criteriaBuilder) -> {
            if (lastName == null) {
                return null;
            }

            String pattern = MessageFormat.format(TEMPLATE_LIKE, lastName.toLowerCase());

            Expression<String> lowerCaseField = criteriaBuilder.lower(root.get(User_.LAST_NAME));

            return criteriaBuilder.like(lowerCaseField, pattern);
        };
    }

    static Specification<User> byEmail(String email) {
        return (root, query, criteriaBuilder) -> {
            if (email == null) {
                return null;
            }

            String pattern = MessageFormat.format(TEMPLATE_LIKE, email.toLowerCase());

            Expression<String> lowerCaseField = criteriaBuilder.lower(root.get(User_.EMAIL));

            return criteriaBuilder.like(lowerCaseField, pattern);
        };
    }

    static Specification<User> byCreateAtBefore(Instant createBefore) {
        return (root, query, criteriaBuilder) -> {
            if (createBefore == null) {
                return null;
            }

            return criteriaBuilder.lessThanOrEqualTo(root.get(User_.CREATE_AT), createBefore);
        };
    }

    static Specification<User> byUpdateAtBefore(Instant updateBefore) {
        return (root, query, criteriaBuilder) -> {
            if (updateBefore == null) {
                return null;
            }

            return criteriaBuilder.lessThanOrEqualTo(root.get(User_.UPDATE_AT), updateBefore);
        };
    }

    static Specification<User> byCreateAtAfter(Instant createAfter) {
        return (root, query, criteriaBuilder) -> {
            if (createAfter == null) {
                return null;
            }

            return criteriaBuilder.greaterThanOrEqualTo(root.get(User_.CREATE_AT), createAfter);
        };
    }

    static Specification<User> byUpdateAtAfter(Instant updateAfter) {
        return (root, query, criteriaBuilder) -> {
            if (updateAfter == null) {
                return null;
            }

            return criteriaBuilder.greaterThanOrEqualTo(root.get(User_.UPDATE_AT), updateAfter);
        };
    }
}
