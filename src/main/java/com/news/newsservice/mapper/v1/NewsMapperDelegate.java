package com.news.newsservice.mapper.v1;

import com.news.newsservice.entity.News;
import com.news.newsservice.service.CategoryService;
import com.news.newsservice.service.UserService;
import com.news.newsservice.web.dto.v1.NewsUpsertRequest;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class NewsMapperDelegate implements NewsMapper {

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @Override
    public News requestToNews(NewsUpsertRequest request) {
        News news = new News();
        news.setText(request.getText());
        news.setUser(userService.findById(request.getUserId()));
        news.setCategory(categoryService.findById(request.getCategoryId()));

        return news;
    }

    @Override
    public News requestToNews(Long newsId, NewsUpsertRequest request) {
        News news = requestToNews(request);
        news.setId(newsId);

        return news;
    }
}
