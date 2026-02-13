package com.news.newsservice.validation;

import com.news.newsservice.web.dto.v1.CommentFilter;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;

public class CommentFilterValidValidator implements ConstraintValidator<CommentFilterValid, CommentFilter> {

    @Override
    public boolean isValid(CommentFilter commentFilter,
                           ConstraintValidatorContext constraintValidatorContext) {
        if (Objects.isNull(commentFilter.pageNumber()) || Objects.isNull(commentFilter.pageSize())) {
            return false;
        }

        return true;
    }
}
