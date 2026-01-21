package com.news.newsservice.web.dto.v1;

import com.news.newsservice.validation.NewsFilterValid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

import static com.news.newsservice.web.dto.FieldsSizes.*;
import static com.news.newsservice.web.dto.PageErrorMessageTemplates.*;
import static com.news.newsservice.web.dto.RegexDto.CYRILLIC_LATIN_DIGITS_SIGNS_REGEX;
import static com.news.newsservice.web.dto.v1.NewsErrorMessageTemplates.*;
import static com.news.newsservice.web.dto.v1.NewsErrorMessageTemplates.VALIDATE_NEWS_TEXT_INCORRECT_REGEX;

@Data
@NoArgsConstructor
@AllArgsConstructor
@NewsFilterValid
public class NewsFilter {

    @Min(value = PAGE_SIZE_MIN, message = VALIDATE_PAGE_SIZE_MIN_INCORRECT)
    @Max(value = PAGE_SIZE_MAX, message = VALIDATE_PAGE_SIZE_MAX_INCORRECT)
    private Integer pageSize;

    @PositiveOrZero(message = VALIDATE_PAGE_NUMBER_INCORRECT)
    private Integer pageNumber;

    @Size(min = FILTER_BIG_TEXT_SIZE_MIN, max = FILTER_BIG_TEXT_SIZE_MAX, message = VALIDATE_NEWS_TEXT_INCORRECT_SIZE)
    @Pattern(regexp = CYRILLIC_LATIN_DIGITS_SIGNS_REGEX, message = VALIDATE_NEWS_TEXT_INCORRECT_REGEX)
    private String text;

    private Instant createBefore;

    private Instant updateBefore;

    private Instant createAfter;

    private Instant updateAfter;

    @Positive(message = VALIDATE_NEWS_USER_ID_INCORRECT)
    private Long userId;

    @Positive(message = VALIDATE_NEWS_CATEGORY_ID_INCORRECT)
    private Long categoryId;
}
