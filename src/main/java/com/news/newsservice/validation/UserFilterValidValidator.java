package com.news.newsservice.validation;

import com.news.newsservice.web.dto.v1.UserFilter;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.ObjectUtils;

public class UserFilterValidValidator implements ConstraintValidator<UserFilterValid, UserFilter> {

    @Override
    public boolean isValid(UserFilter userFilter,
                           ConstraintValidatorContext constraintValidatorContext) {
        if (ObjectUtils.anyNull(userFilter.getPageNumber(), userFilter.getPageSize())) {
            return false;
        }

        return true;
    }
}
