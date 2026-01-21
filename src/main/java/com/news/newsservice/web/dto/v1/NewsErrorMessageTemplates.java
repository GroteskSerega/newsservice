package com.news.newsservice.web.dto.v1;

public class NewsErrorMessageTemplates {

    public static final String VALIDATE_NEWS_TEXT_BLANK =
            "Поле Текст должно быть заполнено";
    public static final String VALIDATE_NEWS_TEXT_INCORRECT_SIZE =
            "Поле Текст должно содержать от {min} до {max} символов";
    public static final String VALIDATE_NEWS_TEXT_INCORRECT_REGEX =
            "Поле Текст должно содержать символы латиницы, кириллицы и знаки препинаний";

    public static final String VALIDATE_NEWS_USER_ID_INCORRECT =
            "Поле ID пользователя должно быть больше 0";

    public static final String VALIDATE_NEWS_CATEGORY_ID_INCORRECT =
            "Поле ID категории должно быть больше 0";
}
