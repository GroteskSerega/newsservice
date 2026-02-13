package com.news.newsservice.web.dto.v1;

import java.util.List;

public record UserListResponse (List<UserResponse> users) {
}

