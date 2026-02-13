package com.news.newsservice.validation;

import com.news.newsservice.web.dto.v1.CategoryFilter;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;

public class CategoryFilterValidValidator implements ConstraintValidator<CategoryFilterValid, CategoryFilter> {

    @Override
    public boolean isValid(CategoryFilter categoryFilter,
                           ConstraintValidatorContext constraintValidatorContext) {
        if (Objects.isNull(categoryFilter.pageNumber()) || Objects.isNull(categoryFilter.pageSize())) {
            return false;
        }

        return true;
    }
}
