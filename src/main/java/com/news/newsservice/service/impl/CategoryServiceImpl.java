package com.news.newsservice.service.impl;

import com.news.newsservice.entity.Category;
import com.news.newsservice.exception.EntityNotFoundException;
import com.news.newsservice.mapper.v1.CategoryMapper;
import com.news.newsservice.repository.CategoryRepository;
import com.news.newsservice.repository.CategorySpecification;
import com.news.newsservice.service.CategoryService;
import com.news.newsservice.web.dto.v1.CategoryFilter;
import com.news.newsservice.web.dto.v1.CategoryUpsertRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

import static com.news.newsservice.service.MessageTemplates.TEMPLATE_CATEGORY_NOT_FOUND_EXCEPTION;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    @Override
    public List<Category> findAll(CategoryFilter filter) {
        return categoryRepository.findAll(CategorySpecification.withFilter(filter),
                PageRequest.of(
                        filter.pageNumber(),
                        filter.pageSize()
                )).getContent();
    }

    @Override
    public Category findById(UUID id) {
        return categoryRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(MessageFormat.format(TEMPLATE_CATEGORY_NOT_FOUND_EXCEPTION, id)));
    }

    @Transactional
    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Transactional
    @Override
    public Category update(UUID categoryId, CategoryUpsertRequest request) {
        Category existedCategory = findById(categoryId);

        categoryMapper.updateCategory(request, existedCategory);

        return categoryRepository.save(existedCategory);
    }

    @Transactional
    @Override
    public void delete(UUID id) {
        findById(id);

        categoryRepository.deleteById(id);
    }
}
