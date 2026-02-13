package com.news.newsservice.web.dto.v1;

import java.util.List;

public record NewsListResponse (List<NewsResponse> news) {
}
