package com.news.newsservice.web.dto.v1;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CommentResponse (
        UUID id,
        String message,
        UserResponse user,
        UUID newsId,
        Instant createAt,
        Instant updateAt) {
}
