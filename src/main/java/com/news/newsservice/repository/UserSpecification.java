package com.news.newsservice.repository;

import com.news.newsservice.entity.User;
import com.news.newsservice.web.dto.v1.UserFilter;
import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;

public interface UserSpecification {

    static Specification<User> withFilter(UserFilter userFilter) {

        return Specification.where(byUsername(userFilter.getUsername()))
                .and(byFirstName(userFilter.getFirstName()))
                .and(bySecondName(userFilter.getSecondName()))
                .and(byLastName(userFilter.getLastName()))
                .and(byEmail(userFilter.getEmail()))
                .and(byCreateAtBefore(userFilter.getCreateBefore()))
                .and(byUpdateAtBefore(userFilter.getUpdateBefore()))
                .and(byCreateAtAfter(userFilter.getCreateAfter()))
                .and(byUpdateAtAfter(userFilter.getUpdateAfter()));
    }

    static Specification<User> byUsername(String username) {
        return (root, query, criteriaBuilder) -> {
            if (username == null) {
                return null;
            }

            return criteriaBuilder.equal(root.get("username"), username);
        };
    }

    static Specification<User> byFirstName(String firstName) {
        return (root, query, criteriaBuilder) -> {
            if (firstName == null) {
                return null;
            }

            return criteriaBuilder.equal(root.get("firstName"), firstName);
        };
    }

    static Specification<User> bySecondName(String secondName) {
        return (root, query, criteriaBuilder) -> {
            if (secondName == null) {
                return null;
            }

            return criteriaBuilder.equal(root.get("secondName"), secondName);
        };
    }

    static Specification<User> byLastName(String lastName) {
        return (root, query, criteriaBuilder) -> {
            if (lastName == null) {
                return null;
            }

            return criteriaBuilder.equal(root.get("lastName"), lastName);
        };
    }

    static Specification<User> byEmail(String email) {
        return (root, query, criteriaBuilder) -> {
            if (email == null) {
                return null;
            }

            return criteriaBuilder.equal(root.get("email"), email);
        };
    }

    static Specification<User> byCreateAtBefore(Instant createBefore) {
        return (root, query, criteriaBuilder) -> {
            if (createBefore == null) {
                return null;
            }

            return criteriaBuilder.lessThanOrEqualTo(root.get("createAt"), createBefore);
        };
    }

    static Specification<User> byUpdateAtBefore(Instant updateBefore) {
        return (root, query, criteriaBuilder) -> {
            if (updateBefore == null) {
                return null;
            }

            return criteriaBuilder.lessThanOrEqualTo(root.get("updateAt"), updateBefore);
        };
    }

    static Specification<User> byCreateAtAfter(Instant createAfter) {
        return (root, query, criteriaBuilder) -> {
            if (createAfter == null) {
                return null;
            }

            return criteriaBuilder.greaterThanOrEqualTo(root.get("createAt"), createAfter);
        };
    }

    static Specification<User> byUpdateAtAfter(Instant updateAfter) {
        return (root, query, criteriaBuilder) -> {
            if (updateAfter == null) {
                return null;
            }

            return criteriaBuilder.greaterThanOrEqualTo(root.get("updateAt"), updateAfter);
        };
    }
}
