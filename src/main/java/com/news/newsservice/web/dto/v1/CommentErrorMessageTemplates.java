package com.news.newsservice.web.dto.v1;

public class CommentErrorMessageTemplates {

    public static final String VALIDATE_COMMENT_MESSAGE_BLANK =
            "Поле Комментарий должно быть заполнено";
    public static final String VALIDATE_COMMENT_MESSAGE_INCORRECT_SIZE =
            "Поле Комментарий должно содержать от {min} до {max} символов";
    public static final String VALIDATE_COMMENT_MESSAGE_INCORRECT_REGEX =
            "Поле Комментарий должно содержать символы латиницы, кириллицы и знаки препинаний";

    public static final String VALIDATE_COMMENT_USER_ID_INCORRECT =
            "Поле ID пользователя должно быть больше 0";

    public static final String VALIDATE_COMMENT_NEWS_ID_INCORRECT =
            "Поле ID новости должно быть больше 0";
}
