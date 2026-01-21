package com.news.newsservice.web.dto.v1;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

import static com.news.newsservice.web.dto.FieldsSizes.*;
import static com.news.newsservice.web.dto.PageErrorMessageTemplates.*;
import static com.news.newsservice.web.dto.RegexDto.CYRILLIC_LATIN_DIGITS_SIGNS_REGEX;
import static com.news.newsservice.web.dto.v1.CommentErrorMessageTemplates.*;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class CommentFilter {

    @Min(value = PAGE_SIZE_MIN, message = VALIDATE_PAGE_SIZE_MIN_INCORRECT)
    @Max(value = PAGE_SIZE_MAX, message = VALIDATE_PAGE_SIZE_MAX_INCORRECT)
    private Integer pageSize;

    @PositiveOrZero(message = VALIDATE_PAGE_NUMBER_INCORRECT)
    private Integer pageNumber;

    @Size(min = BIG_TEXT_SIZE_MIN, max = BIG_TEXT_SIZE_MAX, message = VALIDATE_COMMENT_MESSAGE_INCORRECT_SIZE)
    @Pattern(regexp = CYRILLIC_LATIN_DIGITS_SIGNS_REGEX, message = VALIDATE_COMMENT_MESSAGE_INCORRECT_REGEX)
    private String message;

    private Instant createBefore;

    private Instant updateBefore;

    private Instant createAfter;

    private Instant updateAfter;

    @Positive(message = VALIDATE_COMMENT_USER_ID_INCORRECT)
    private Long userId;

    @Positive(message = VALIDATE_COMMENT_NEWS_ID_INCORRECT)
    private Long newsId;
}
