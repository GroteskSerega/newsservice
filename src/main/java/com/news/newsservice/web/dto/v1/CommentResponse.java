package com.news.newsservice.web.dto.v1;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {

    private Long id;

    private String message;

    private UserResponse user;

//    private NewsResponse news;
    private Long newsId;

    private Instant createAt;

    private Instant updateAt;
}
