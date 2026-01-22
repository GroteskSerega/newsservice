package com.news.newsservice.service;

import com.news.newsservice.entity.Category;
import com.news.newsservice.web.dto.v1.CategoryFilter;

import java.util.List;


public interface CategoryService {

    List<Category> findAll(CategoryFilter filter);

    Category findById(Long id);

    Category save(Category category);

    Category update(Category category);

    void deleteById(Long id);
}
