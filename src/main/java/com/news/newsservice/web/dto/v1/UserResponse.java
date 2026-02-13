package com.news.newsservice.web.dto.v1;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserResponse (

        UUID id,
        String username,
        String firstName,
        String secondName,
        String lastName,
        String email,
        Instant createAt,
        Instant updateAt
) {

}
