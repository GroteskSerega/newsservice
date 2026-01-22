package com.news.newsservice.service.impl;

import com.news.newsservice.entity.Category;
import com.news.newsservice.exception.EntityNotFoundException;
import com.news.newsservice.repository.CategoryRepository;
import com.news.newsservice.repository.CategorySpecification;
import com.news.newsservice.service.CategoryService;
import com.news.newsservice.utils.BeanUtils;
import com.news.newsservice.web.dto.v1.CategoryFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

import static com.news.newsservice.service.MessageTemplates.TEMPLATE_CATEGORY_NOT_FOUND_EXCEPTION;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> findAll(CategoryFilter filter) {
        return categoryRepository.findAll(CategorySpecification.withFilter(filter),
                PageRequest.of(
                        filter.getPageNumber(),
                        filter.getPageSize()
                )).getContent();
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(MessageFormat.format(TEMPLATE_CATEGORY_NOT_FOUND_EXCEPTION, id)));
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category update(Category category) {
        Category existedCategory = findById(category.getId());

        BeanUtils.copyNonNullProperties(category, existedCategory);

        return categoryRepository.save(existedCategory);
    }

    @Override
    public void deleteById(Long id) {
        findById(id);

        categoryRepository.deleteById(id);
    }
}
