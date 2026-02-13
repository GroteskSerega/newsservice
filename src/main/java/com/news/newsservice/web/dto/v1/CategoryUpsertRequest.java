package com.news.newsservice.web.dto.v1;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import static com.news.newsservice.web.dto.FieldsSizes.NAME_SIZE_MAX;
import static com.news.newsservice.web.dto.FieldsSizes.NAME_SIZE_MIN;
import static com.news.newsservice.web.dto.RegexDto.CYRILLIC_LATIN_SPACE_REGEX;
import static com.news.newsservice.web.dto.v1.CategoryErrorMessageTemplates.*;

public record CategoryUpsertRequest (

        @NotBlank(message = VALIDATE_CATEGORY_NAME_BLANK)
        @Size(min = NAME_SIZE_MIN, max = NAME_SIZE_MAX, message = VALIDATE_CATEGORY_NAME_INCORRECT_SIZE)
        @Pattern(regexp = CYRILLIC_LATIN_SPACE_REGEX, message = VALIDATE_CATEGORY_NAME_INCORRECT_REGEX)
        String category) {
}
