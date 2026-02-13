package com.news.newsservice.service;

import com.news.newsservice.entity.Category;
import com.news.newsservice.web.dto.v1.CategoryFilter;
import com.news.newsservice.web.dto.v1.CategoryUpsertRequest;

import java.util.List;
import java.util.UUID;


public interface CategoryService {

    List<Category> findAll(CategoryFilter filter);;

    Category findById(UUID id);

    Category save(Category category);

    Category update(UUID categoryId, CategoryUpsertRequest request);

    void delete(UUID id);
}
