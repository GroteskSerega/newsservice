package com.news.newsservice.validation;

import com.news.newsservice.web.dto.v1.NewsFilter;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;

public class NewsFilterValidValidator implements ConstraintValidator<NewsFilterValid, NewsFilter> {

    @Override
    public boolean isValid(NewsFilter newsFilter,
                           ConstraintValidatorContext constraintValidatorContext) {
        if (Objects.isNull(newsFilter.pageNumber()) || Objects.isNull(newsFilter.pageSize())) {
            return false;
        }

        return true;
    }
}
