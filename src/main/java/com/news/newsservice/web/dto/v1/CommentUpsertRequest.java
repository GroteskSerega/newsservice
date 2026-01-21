package com.news.newsservice.web.dto.v1;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.news.newsservice.web.dto.FieldsSizes.BIG_TEXT_SIZE_MAX;
import static com.news.newsservice.web.dto.FieldsSizes.BIG_TEXT_SIZE_MIN;
import static com.news.newsservice.web.dto.RegexDto.CYRILLIC_LATIN_DIGITS_SIGNS_REGEX;
import static com.news.newsservice.web.dto.v1.CommentErrorMessageTemplates.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentUpsertRequest {

    @NotNull(message = VALIDATE_COMMENT_MESSAGE_BLANK)
    @Size(min = BIG_TEXT_SIZE_MIN, max = BIG_TEXT_SIZE_MAX, message = VALIDATE_COMMENT_MESSAGE_INCORRECT_SIZE)
    @Pattern(regexp = CYRILLIC_LATIN_DIGITS_SIGNS_REGEX, message = VALIDATE_COMMENT_MESSAGE_INCORRECT_REGEX)
    private String message;

    @Positive(message = VALIDATE_COMMENT_USER_ID_INCORRECT)
    private Long userId;

    @Positive(message = VALIDATE_COMMENT_NEWS_ID_INCORRECT)
    private Long newsId;
}
