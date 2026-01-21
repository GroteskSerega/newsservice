package com.news.newsservice.mapper.v1;

import com.news.newsservice.entity.Category;
import com.news.newsservice.web.dto.v1.CategoryListResponse;
import com.news.newsservice.web.dto.v1.CategoryResponse;
import com.news.newsservice.web.dto.v1.CategoryUpsertRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {NewsMapper.class})
public interface CategoryMapper {

    Category requestToCategory(CategoryUpsertRequest request);

    @Mapping(source = "categoryId", target = "id")
    Category requestToCategory(Long categoryId,
                               CategoryUpsertRequest request);

    CategoryResponse categoryToResponse(Category category);

    default CategoryListResponse categoryListToCategoryListResponse(List<Category> categories) {
        CategoryListResponse response = new CategoryListResponse();

        response.setCategories(categories
                .stream()
                .map(this::categoryToResponse)
                .collect(Collectors.toList()));

        return response;
    }
}
