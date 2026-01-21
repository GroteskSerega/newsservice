package com.news.newsservice.service;

import com.news.newsservice.entity.News;
import com.news.newsservice.web.dto.v1.NewsFilter;

import java.util.List;

public interface NewsService {

    List<News> filterBy(NewsFilter filter);

    List<News> findAll();

    News findById(Long id);

    News save(News news);

    News update(News news);

    void delete(Long id);
}
