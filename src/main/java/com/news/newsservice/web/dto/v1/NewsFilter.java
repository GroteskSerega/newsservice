package com.news.newsservice.web.dto.v1;

import com.news.newsservice.validation.NewsFilterValid;
import jakarta.validation.constraints.*;

import java.time.Instant;
import java.util.UUID;

import static com.news.newsservice.web.dto.FieldsSizes.*;
import static com.news.newsservice.web.dto.PageErrorMessageTemplates.*;
import static com.news.newsservice.web.dto.RegexDto.CYRILLIC_LATIN_DIGITS_SIGNS_REGEX;
import static com.news.newsservice.web.dto.v1.NewsErrorMessageTemplates.*;
import static com.news.newsservice.web.dto.v1.NewsErrorMessageTemplates.VALIDATE_NEWS_TEXT_INCORRECT_REGEX;

@NewsFilterValid
public record NewsFilter (

        @Min(value = PAGE_SIZE_MIN, message = VALIDATE_PAGE_SIZE_MIN_INCORRECT)
        @Max(value = PAGE_SIZE_MAX, message = VALIDATE_PAGE_SIZE_MAX_INCORRECT)
        Integer pageSize,

        @PositiveOrZero(message = VALIDATE_PAGE_NUMBER_INCORRECT)
        Integer pageNumber,

        @Size(min = FILTER_BIG_TEXT_SIZE_MIN, max = FILTER_BIG_TEXT_SIZE_MAX, message = VALIDATE_NEWS_TEXT_INCORRECT_SIZE)
        @Pattern(regexp = CYRILLIC_LATIN_DIGITS_SIGNS_REGEX, message = VALIDATE_NEWS_TEXT_INCORRECT_REGEX)
        String text,

        Instant createBefore,

        Instant updateBefore,

        Instant createAfter,

        Instant updateAfter,

//        @Positive(message = VALIDATE_NEWS_USER_ID_INCORRECT)
        UUID userId,

//        @Positive(message = VALIDATE_NEWS_CATEGORY_ID_INCORRECT)
        UUID categoryId) {
}
