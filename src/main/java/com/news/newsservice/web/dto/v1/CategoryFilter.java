package com.news.newsservice.web.dto.v1;


import com.news.newsservice.validation.CategoryFilterValid;
import jakarta.validation.constraints.*;

import java.time.Instant;

import static com.news.newsservice.web.dto.FieldsSizes.*;
import static com.news.newsservice.web.dto.PageErrorMessageTemplates.*;
import static com.news.newsservice.web.dto.RegexDto.CYRILLIC_LATIN_SPACE_REGEX;
import static com.news.newsservice.web.dto.v1.CategoryErrorMessageTemplates.VALIDATE_CATEGORY_NAME_INCORRECT_REGEX;
import static com.news.newsservice.web.dto.v1.CategoryErrorMessageTemplates.VALIDATE_CATEGORY_NAME_INCORRECT_SIZE;

@CategoryFilterValid
public record CategoryFilter (

        @Min(value = PAGE_SIZE_MIN, message = VALIDATE_PAGE_SIZE_MIN_INCORRECT)
        @Max(value = PAGE_SIZE_MAX, message = VALIDATE_PAGE_SIZE_MAX_INCORRECT)
        Integer pageSize,

        @PositiveOrZero(message = VALIDATE_PAGE_NUMBER_INCORRECT)
        Integer pageNumber,

        @Size(min = NAME_SIZE_MIN, max = NAME_SIZE_MAX, message = VALIDATE_CATEGORY_NAME_INCORRECT_SIZE)
        @Pattern(regexp = CYRILLIC_LATIN_SPACE_REGEX, message = VALIDATE_CATEGORY_NAME_INCORRECT_REGEX)
        String category,

        Instant createBefore,

        Instant updateBefore,

        Instant createAfter,

        Instant updateAfter) {
}
