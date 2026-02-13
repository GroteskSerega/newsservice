package com.news.newsservice.web.dto.v1;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record NewsResponse (
        UUID id,
        String text,
        UserResponse user,
        CategoryResponse category,
        List<CommentResponse> comments,
        Integer countComments,
        Instant createAt,
        Instant updateAt) {
}
