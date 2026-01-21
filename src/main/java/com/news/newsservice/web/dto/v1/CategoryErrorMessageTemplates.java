package com.news.newsservice.web.dto.v1;

public class CategoryErrorMessageTemplates {

    public static final String VALIDATE_CATEGORY_NAME_BLANK =
            "Поле Название категории должно быть заполнено";
    public static final String VALIDATE_CATEGORY_NAME_INCORRECT_SIZE =
            "Поле Название категории должно содержать от {min} до {max} символов";
    public static final String VALIDATE_CATEGORY_NAME_INCORRECT_REGEX =
            "Поле Название категории должно содержать символы кириллицы или латинницы или пробела";
}
