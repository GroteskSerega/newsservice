package com.news.newsservice.validation;

import com.news.newsservice.web.dto.v1.UserFilter;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;

public class UserFilterValidValidator implements ConstraintValidator<UserFilterValid, UserFilter> {

    @Override
    public boolean isValid(UserFilter userFilter,
                           ConstraintValidatorContext constraintValidatorContext) {
        if (Objects.isNull(userFilter.pageNumber()) || Objects.isNull(userFilter.pageSize())) {
            return false;
        }

        return true;
    }
}
