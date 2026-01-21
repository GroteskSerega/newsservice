package com.news.newsservice.validation;

import com.news.newsservice.web.dto.v1.CategoryFilter;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.ObjectUtils;

public class CategoryFilterValidValidator implements ConstraintValidator<CategoryFilterValid, CategoryFilter> {

    @Override
    public boolean isValid(CategoryFilter categoryFilter,
                           ConstraintValidatorContext constraintValidatorContext) {
        if (ObjectUtils.anyNull(categoryFilter.getPageNumber(), categoryFilter.getPageSize())) {
            return false;
        }

        return true;
    }
}
