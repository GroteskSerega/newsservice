package com.news.newsservice.web.dto.v1;

import com.news.newsservice.validation.UserFilterValid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

import static com.news.newsservice.web.dto.FieldsSizes.*;
import static com.news.newsservice.web.dto.FieldsSizes.NAME_SIZE_MAX;
import static com.news.newsservice.web.dto.PageErrorMessageTemplates.*;
import static com.news.newsservice.web.dto.RegexDto.*;
import static com.news.newsservice.web.dto.v1.UserErrorMessageTemplates.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@UserFilterValid
public class UserFilter {

    @Min(value = PAGE_SIZE_MIN, message = VALIDATE_PAGE_SIZE_MIN_INCORRECT)
    @Max(value = PAGE_SIZE_MAX, message = VALIDATE_PAGE_SIZE_MAX_INCORRECT)
    private Integer pageSize;

    @PositiveOrZero(message = VALIDATE_PAGE_NUMBER_INCORRECT)
    private Integer pageNumber;

    @Size(min = NAME_SIZE_MIN, max = NAME_SIZE_MAX, message = VALIDATE_USER_USERNAME_INCORRECT_SIZE)
    @Pattern(regexp = LATIN_REGEX, message = VALIDATE_USER_USERNAME_INCORRECT_REGEX)
    private String username;

    @Size(min = NAME_SIZE_MIN, max = NAME_SIZE_MAX, message = VALIDATE_USER_FIRSTNAME_INCORRECT_SIZE)
    @Pattern(regexp = CYRILLIC_REGEX, message = VALIDATE_USER_FIRSTNAME_INCORRECT_REGEX)
    private String firstName;

    @Size(min = NAME_SIZE_MIN, max = NAME_SIZE_MAX, message = VALIDATE_USER_SECONDNAME_INCORRECT_SIZE)
    @Pattern(regexp = CYRILLIC_REGEX, message = VALIDATE_USER_SECONDNAME_INCORRECT_REGEX)
    private String secondName;

    @Size(min = NAME_SIZE_MIN, max = NAME_SIZE_MAX, message = VALIDATE_USER_LASTNAME_INCORRECT_SIZE)
    @Pattern(regexp = CYRILLIC_REGEX, message = VALIDATE_USER_LASTNAME_INCORRECT_REGEX)
    private String lastName;

    @Size(min = NAME_SIZE_MIN, max = NAME_SIZE_MAX, message = VALIDATE_USER_EMAIL_INCORRECT_SIZE)
    @Email(regexp = EMAIL_REGEX, message = VALIDATE_USER_EMAIL_INCORRECT_REGEX)
    private String email;

    private Instant createBefore;

    private Instant updateBefore;

    private Instant createAfter;

    private Instant updateAfter;
}
