package com.news.newsservice.web.dto.v1;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.news.newsservice.web.dto.FieldsSizes.*;
import static com.news.newsservice.web.dto.RegexDto.*;
import static com.news.newsservice.web.dto.v1.UserErrorMessageTemplates.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpsertRequest {

    @NotBlank(message = VALIDATE_USER_USERNAME_BLANK)
    @Size(min = NAME_SIZE_MIN, max = NAME_SIZE_MAX, message = VALIDATE_USER_USERNAME_INCORRECT_SIZE)
    @Pattern(regexp = LATIN_REGEX, message = VALIDATE_USER_USERNAME_INCORRECT_REGEX)
    private String username;

    @NotBlank(message = VALIDATE_USER_PASSWORD_BLANK)
    @Size(min = PASSWORD_SIZE_MIN, max = PASSWORD_SIZE_MAX, message = VALIDATE_USER_PASSWORD_INCORRECT_SIZE)
    private String password;

    @NotBlank(message = VALIDATE_USER_FIRSTNAME_BLANK)
    @Size(min = NAME_SIZE_MIN, max = NAME_SIZE_MAX, message = VALIDATE_USER_FIRSTNAME_INCORRECT_SIZE)
    @Pattern(regexp = CYRILLIC_REGEX, message = VALIDATE_USER_FIRSTNAME_INCORRECT_REGEX)
    private String firstName;

    @Size(min = NAME_SIZE_MIN, max = NAME_SIZE_MAX, message = VALIDATE_USER_SECONDNAME_INCORRECT_SIZE)
    @Pattern(regexp = CYRILLIC_REGEX, message = VALIDATE_USER_SECONDNAME_INCORRECT_REGEX)
    private String secondName;

    @NotBlank(message = VALIDATE_USER_LASTNAME_BLANK)
    @Size(min = NAME_SIZE_MIN, max = NAME_SIZE_MAX, message = VALIDATE_USER_LASTNAME_INCORRECT_SIZE)
    @Pattern(regexp = CYRILLIC_REGEX, message = VALIDATE_USER_LASTNAME_INCORRECT_REGEX)
    private String lastName;

    @NotBlank(message = VALIDATE_USER_EMAIL_BLANK)
    @Size(min = NAME_SIZE_MIN, max = NAME_SIZE_MAX, message = VALIDATE_USER_EMAIL_INCORRECT_SIZE)
    @Email(regexp = EMAIL_REGEX, message = VALIDATE_USER_EMAIL_INCORRECT_REGEX)
    private String email;
}
