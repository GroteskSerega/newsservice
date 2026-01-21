package com.news.newsservice.web.dto.v1;

public class UserErrorMessageTemplates {

    public static final String VALIDATE_USER_USERNAME_BLANK =
            "Поле Логин пользователя должно быть заполнено";
    public static final String VALIDATE_USER_USERNAME_INCORRECT_SIZE =
            "Поле Логин пользователя должно содержать от {min} до {max} символов";
    public static final String VALIDATE_USER_USERNAME_INCORRECT_REGEX =
            "Поле Логин пользователя должно содержать символы латиницы";

    public static final String VALIDATE_USER_PASSWORD_BLANK =
            "Поле Пароль должно быть заполнен";
    public static final String VALIDATE_USER_PASSWORD_INCORRECT_SIZE =
            "Поле Пароль должно содержать от {min} до {max} символов";

    public static final String VALIDATE_USER_FIRSTNAME_BLANK =
            "Поле Имя пользователя должно быть заполнено";
    public static final String VALIDATE_USER_FIRSTNAME_INCORRECT_SIZE =
            "Поле Имя пользователя должно содержать от {min} до {max} символов";
    public static final String VALIDATE_USER_FIRSTNAME_INCORRECT_REGEX =
            "Поле Имя пользователя должно содержать символы кириллицы";

    public static final String VALIDATE_USER_SECONDNAME_INCORRECT_SIZE =
            "Поле Отчество пользователя должно содержать от {min} до {max} символов";
    public static final String VALIDATE_USER_SECONDNAME_INCORRECT_REGEX =
            "Поле Отчество пользователя должно содержать символы кириллицы";

    public static final String VALIDATE_USER_LASTNAME_BLANK =
            "Поле Фамилия пользователя должно быть заполнено";
    public static final String VALIDATE_USER_LASTNAME_INCORRECT_SIZE =
            "Поле Фамилия пользователя должно содержать от {min} до {max} символов";
    public static final String VALIDATE_USER_LASTNAME_INCORRECT_REGEX =
            "Поле Фамилия пользователя должно содержать символы кириллицы";

    public static final String VALIDATE_USER_EMAIL_BLANK =
            "Поле Email должно быть заполнено";
    public static final String VALIDATE_USER_EMAIL_INCORRECT_SIZE =
            "Поле Email должно содержать от {min} до {max} символов";
    public static final String VALIDATE_USER_EMAIL_INCORRECT_REGEX =
            "Поле Email должно соответствовать маске ***@***.***, где * - символ цифры или латинского алфавита";

    public static final String VALIDATE_USER_INSTANT_INCORRECT_REGEX =
            "Поле Дата должно соответствовать маске 20**-**-**T**:**:**.******Z, где * - символ цифра";
}
