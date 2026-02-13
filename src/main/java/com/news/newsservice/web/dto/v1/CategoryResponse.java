package com.news.newsservice.web.dto.v1;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CategoryResponse (UUID id, String category) {
}
