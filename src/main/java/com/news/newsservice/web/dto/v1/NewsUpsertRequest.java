package com.news.newsservice.web.dto.v1;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.news.newsservice.web.dto.FieldsSizes.BIG_TEXT_SIZE_MAX;
import static com.news.newsservice.web.dto.FieldsSizes.BIG_TEXT_SIZE_MIN;
import static com.news.newsservice.web.dto.RegexDto.CYRILLIC_LATIN_DIGITS_SIGNS_REGEX;
import static com.news.newsservice.web.dto.v1.NewsErrorMessageTemplates.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsUpsertRequest {

    @NotNull(message = VALIDATE_NEWS_TEXT_BLANK)
    @Size(min = BIG_TEXT_SIZE_MIN, max = BIG_TEXT_SIZE_MAX, message = VALIDATE_NEWS_TEXT_INCORRECT_SIZE)
    @Pattern(regexp = CYRILLIC_LATIN_DIGITS_SIGNS_REGEX, message = VALIDATE_NEWS_TEXT_INCORRECT_REGEX)
    private String text;

//    @NotBlank(message = "ID пользователя должно быть указано")
    @Positive(message = VALIDATE_NEWS_USER_ID_INCORRECT)
    private Long userId;

//    @NotBlank(message = "ID категории должно быть указано")
    @Positive(message = VALIDATE_NEWS_CATEGORY_ID_INCORRECT)
    private Long categoryId;
}
