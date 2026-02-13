package com.news.newsservice.service;

import com.news.newsservice.entity.News;
import com.news.newsservice.web.dto.v1.NewsFilter;
import com.news.newsservice.web.dto.v1.NewsUpsertRequest;

import java.util.List;
import java.util.UUID;

public interface NewsService {

    List<News> findAll(NewsFilter filter);

    News findById(UUID id);

    News save(News news);

    News update(UUID newsId, NewsUpsertRequest request);

    UUID findUserIdById(UUID id);

    void delete(UUID id);
}
