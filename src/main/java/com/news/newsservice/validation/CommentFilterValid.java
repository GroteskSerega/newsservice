package com.news.newsservice.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

import static com.news.newsservice.validation.ValidErrorMessageTemplates.FIELDS_PAGE_REQUEST_NULL;

@Documented
@Constraint(validatedBy = CommentFilterValidValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CommentFilterValid {

    String message() default FIELDS_PAGE_REQUEST_NULL;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
