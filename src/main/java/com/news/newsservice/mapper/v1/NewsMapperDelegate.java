package com.news.newsservice.mapper.v1;

import com.news.newsservice.entity.News;
import com.news.newsservice.service.CategoryService;
import com.news.newsservice.service.UserService;
import com.news.newsservice.web.dto.v1.NewsUpsertRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

public abstract class NewsMapperDelegate implements NewsMapper {

    private UserService userService;

    private CategoryService categoryService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public News requestToNews(NewsUpsertRequest request, UserDetails userDetails) {
        News news = new News();
        news.setText(request.text());
        news.setUser(userService.findByUsername(userDetails.getUsername()));
        news.setCategory(categoryService.findById(request.categoryId()));

        return news;
    }
}
