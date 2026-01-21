package com.news.newsservice.validation;

import com.news.newsservice.web.dto.v1.CommentFilter;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.ObjectUtils;

public class CommentFilterValidValidator implements ConstraintValidator<CommentFilterValid, CommentFilter> {

    @Override
    public boolean isValid(CommentFilter commentFilter,
                           ConstraintValidatorContext constraintValidatorContext) {
        if (ObjectUtils.anyNull(commentFilter.getPageNumber(), commentFilter.getPageSize())) {
            return false;
        }

        return true;
    }
}
